package models

import shared.SharedMessages.Message

class OneMessage {
  private var _message: Message = Message("test", "root")
  private def message = _message

  def changeMessage(msg: Message): Message = {
    val oldMessage = message
    _message = msg
    oldMessage
  }
}
