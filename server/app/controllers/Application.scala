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

@Singleton
class Application @Inject()(protected val dbConfigProvider: DatabaseConfigProvider, cc: ControllerComponents)(implicit ec: ExecutionContext, system: ActorSystem, mat: Materializer)
  extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {
  val manager = system.actorOf(CountingManager.props, "Manager")
  private val model = new DatabaseRelationshipModel(db)
  
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
}
