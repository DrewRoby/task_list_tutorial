package controllers

import play.api.i18n._
import play.api.mvc._

import javax.inject._

@Singleton
class TaskList1 @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  def taskList = Action {
    Ok("This works!")
  }

}
