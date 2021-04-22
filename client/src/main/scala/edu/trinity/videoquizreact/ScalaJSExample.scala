package edu.trinity.videoquizreact

import shared.SharedMessages
import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.html
import shared.SharedMessages.Message
import shared.SharedMessages.ReadsAndWrites._
import scala.concurrent.ExecutionContext
import org.scalajs.dom.experimental._
import scala.scalajs.js.Thenable.Implicits._
import scala.scalajs.js.JSON
import play.api.libs.json._

object ScalaJSExample {
  implicit val ec = ExecutionContext.global
  var counter = 0

  def main(args: Array[String]): Unit = {
    // dom.document.getElementById("scalajsShoutOut").textContent = SharedMessages.itWorks
    // println("Call the react stuff.")
    // ReactDOM.render(
    //   h1("Hello, world!"),
    //   dom.document.getElementById("root")
    // )

    // println("Counting time!")
    // for (i <- 1 to 10) {
    //   println(i)
    // }

    // document.getElementById("countingButton").addEventListener("click", (e: dom.Event) => {
    //   counter += 1
    //   document.getElementById("scalajs-counter").innerHTML = counter.toString
    // })

    if (document.getElementById("oneMessage") != null) {
      oneMessage()
    }
  }

  def oneMessage(): Unit = {
    document.getElementById("postButton").addEventListener("click", (e: dom.Event) => {
      val author = document.getElementById("authorLabel").asInstanceOf[html.Input].value
      val body = document.getElementById("bodyLabel").asInstanceOf[html.Input].value
      val message = Message(body, author)

      fetch[Message, Message]("/oneMessage/post", message, oldMessage => {
        document.getElementById("lastMessageAuthorLabel").innerHTML = oldMessage.author
        document.getElementById("lastMessageBodyLabel").innerHTML = oldMessage.text
      },
      error => {
        println(error)
      })
    })
  }

  def fetch[A, B](url: String, data: A, success: B => Unit, error: JsError => Unit)(implicit writes: Writes[A], reads: Reads[B]): Unit = {
    val hs = new Headers()
    hs.set("Content-Type", "application/json")
    hs.set("Csrf-Token", dom.document.getElementsByTagName("body").apply(0).getAttribute("data-token"))
    Fetch.fetch(
      url,
      new RequestInit {
        method = HttpMethod.POST
        headers = hs
        body = Json.toJson(data).toString
      }
    ).flatMap(_.text()).map { res =>
      Json.fromJson[B](Json.parse(res)) match {
        case JsSuccess(ret, path) => 
          success(ret)
        case e @ JsError(_) => 
          println("Fetch error " + e)
          error(e)
      }
    }
  }
}
