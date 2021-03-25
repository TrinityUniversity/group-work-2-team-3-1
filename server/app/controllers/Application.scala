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

@Singleton
class Application @Inject()(cc: ControllerComponents)(implicit system: ActorSystem, mat: Materializer) extends AbstractController(cc) {
  val manager = system.actorOf(CountingManager.props, "Manager")
  
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
}
