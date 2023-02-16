package controllers

import play.api.data._
import play.api.i18n._
import play.api.mvc._

import javax.inject._

@Singleton
class TaskList2 @Inject() (cc: ControllerComponents) extends AbstractController(cc) {
  def load = Action {
    Ok("Single Page")
  }
}
