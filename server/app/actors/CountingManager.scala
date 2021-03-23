package actors

import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorRef
import models.CountingModel

class CountingManager extends Actor {
  private var counters = List.empty[ActorRef]

  import CountingManager._
  def receive = {
    case NewCounter(counter) => counters ::= counter
    case IncrementCounter => {
      CountingModel.incrementCounter()
      val newValue = CountingModel.getCounter()
      for (c <- counters) c ! CountingActor.UpdateCounter(newValue)
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
