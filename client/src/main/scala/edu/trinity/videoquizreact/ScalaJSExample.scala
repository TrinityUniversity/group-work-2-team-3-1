package edu.trinity.videoquizreact

import shared.SharedMessages
import org.scalajs.dom
import org.scalajs.dom.document
import slinky.core._
import slinky.web.ReactDOM
import slinky.web.html._

object ScalaJSExample {
  var counter = 0

  def main(args: Array[String]): Unit = {
    // dom.document.getElementById("scalajsShoutOut").textContent = SharedMessages.itWorks
    // println("Call the react stuff.")
    // ReactDOM.render(
    //   h1("Hello, world!"),
    //   dom.document.getElementById("root")
    // )

    println("Counting time!")
    for (i <- 1 to 10) {
      println(i)
    }

    document.getElementById("countingButton").addEventListener("click", (e: dom.Event) => {
      counter += 1
      document.getElementById("scalajs-counter").innerHTML = counter.toString
    })
  }
}
