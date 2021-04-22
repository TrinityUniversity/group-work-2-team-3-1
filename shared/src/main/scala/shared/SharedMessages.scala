package shared

import play.api.libs.json._

object SharedMessages {

  def itWorks = "It works!"

  case class Message(text: String, author: String)

  object ReadsAndWrites {
    implicit val messageReads = Json.reads[Message]
    implicit val messageWrites = Json.writes[Message]
  }
}
