package edu.trinity.videoquizreact

import slinky.core.annotations.react
import slinky.core.Component
import slinky.web.html._

@react class OneMessageComponent extends Component {
  type Props = Unit
  case class State(sender: String, body: String)
  def initialState = State("", "")
  
  def render() = div (
    h1 ("OneMessage"),
    h2 ("Compose a Message"),
    label ("Author"),
    input (`type` := "text", name := "author"),
    label ("Body"),
    input (`type` := "text", name := "body"),

    h2 ("Last Message"),
    h3 (state.sender),
    p (state.body),
  )
}