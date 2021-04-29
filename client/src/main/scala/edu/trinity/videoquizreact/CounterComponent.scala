package edu.trinity.videoquizreact

import slinky.core.annotations.react
import slinky.core.Component
import slinky.web.html._

@react class CounterComponent extends Component {
  type Props = Unit
  case class State(counter: Int)

  def initialState = State(0)

  def render() = div (
    button ("Increment", onClick := (() => setState(state.copy(counter = state.counter + 1)))),
    p ("Counter: " + state.counter)
  )
}