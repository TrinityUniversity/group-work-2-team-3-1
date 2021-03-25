package actors

import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorRef

class CountingManager extends Actor {
  private var counters = List.empty[ActorRef]
  private var counter: Int = 0

  import CountingManager._
  def receive = {
    case NewCounter(actor) => {
      counters ::= actor
      actor ! CountingActor.UpdateCounter(counter)
    }
    case IncrementCounter => {
      counter += 1
      for (c <- counters) c ! CountingActor.UpdateCounter(counter)
    }
    case m => println(s"Manager received $m")
  }
}

object CountingManager {
  def props = {
    Props(new CountingManager)
  }

  case class NewCounter(counter: ActorRef)
  case object IncrementCounter
}
