package actors

import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorRef

class CountingActor(out: ActorRef, manager: ActorRef) extends Actor {
  manager ! CountingManager.NewCounter(self)

  import CountingActor._
  def receive = {
    case UpdateCounter(value) => {
      out ! value.toString()
    }
    case IncrementCounter => manager ! CountingManager.IncrementCounter
    case m: String => self ! IncrementCounter
    case m => println("Unexpected message in CountingActor: " + m)
  }
}

object CountingActor {
  def props(out: ActorRef, manager: ActorRef) = Props(new CountingActor(out, manager))

  case object IncrementCounter
  case class UpdateCounter(newValue: Int)
}