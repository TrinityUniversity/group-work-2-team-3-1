package edu.trinity.videoquizreact

import slinky.core.annotations.react
import slinky.core.Component
import slinky.web.html._
import shared.SharedMessages.Message
import shared.SharedMessages.ReadsAndWrites._

@react class OneMessageComponent extends Component {
  type Props = Unit
  case class State(sender: String, body: String, oldSender: String, oldBody: String)
  def initialState = State("", "", "", "")

  def postMessage() = {
    val message = Message(state.body, state.sender)
    ScalaJSExample.fetch[Message, Message]("/oneMessage/post", message, (messageResp) => {
      setState(state.copy(oldSender = messageResp.author, oldBody = messageResp.text))
    }, (error) => {
      println("error posting message: " + error)
    })
  }
  
  def render() = div (
    h1 ("OneMessage"),
    h2 ("Compose a Message"),
    label ("Author"),
    input (`type` := "text", name := "author", onChange := (e => setState(state.copy(sender = e.target.value)))),
    label ("Body"),
    input (`type` := "text", name := "body", onChange := (e => setState(state.copy(body = e.target.value)))),
    button ("Send Message", onClick := (e => postMessage())),

    h2 ("Last Message"),
    h3 (state.oldSender),
    p (state.oldBody),
  )
}