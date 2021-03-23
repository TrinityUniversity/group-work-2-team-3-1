package controllers

import javax.inject._

import models.CountingModel
import play.api.mvc._

@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action {
    Ok(views.html.index(CountingModel.getCounter()))
  }

  def getCounter = Action {
    Ok(CountingModel.getCounter().toString())
  }

  def incrementCounter = Action {
    CountingModel.incrementCounter()
    Ok(CountingModel.getCounter().toString())
  }
}
