package controllers

import javax.inject._

import models.CountingModel
import play.api.mvc._
import akka.actor.ActorSystem
import akka.stream.Materializer
import actors.CountingActor
import actors.CountingManager
import akka.actor.Props
import play.api.libs.streams.ActorFlow
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import slick.driver.JdbcProfile
import models.DatabaseRelationshipModel
import scala.concurrent.ExecutionContext
import play.api.libs.json.JsSuccess
import play.api.libs.json._
import play.api.libs.json.JsError
import shared.SharedMessages.ReadsAndWrites._

@Singleton
class Application @Inject()(protected val dbConfigProvider: DatabaseConfigProvider, cc: ControllerComponents)(implicit ec: ExecutionContext, system: ActorSystem, mat: Materializer)
  extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {
  val manager = system.actorOf(CountingManager.props, "Manager")
  private val model = new DatabaseRelationshipModel(db)
  private val oneMessageModel = new models.OneMessage()
  
  def index = Action {
    Ok(views.html.index(CountingModel.getCounter()))
  }

  def getCounter = Action {
    Ok(CountingModel.getCounter().toString())
  }

  def incrementCounter = Action {
    CountingModel.incrementCounter()
    Ok(CountingModel.getCounter().toString())
  }

 def socket = WebSocket.accept[String, String] { request =>
    ActorFlow.actorRef { out =>
      CountingActor.props(out, manager)
    }
  }

  def react = Action {
    Ok(views.html.basic())
  }

  def relationships = Action.async {
    model.getRelationships().map(relationships => {
      Ok(views.html.relationships(relationships))
    })
  }

  def oneMessage = Action {
    Ok(views.html.oneMessage())
  }

  def oneMessagePost = Action { request =>
    request.body.asJson.map { body => 
      Json.fromJson[shared.SharedMessages.Message](body) match {
        case JsSuccess(message, _) =>
          val oldMessage = oneMessageModel.changeMessage(message)
          Ok(Json.toJson(oldMessage))
        case e @ JsError(_) => BadRequest("Bad")
      }
    }.getOrElse(BadRequest("Bad"))
  }

  def slinky = Action {
    Ok(views.html.slinky())
  }
}
