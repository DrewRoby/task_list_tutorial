package controllers


import play.api.mvc._

import javax.inject._


@Singleton
class TaskList4 @Inject() (cc: ControllerComponents) extends AbstractController(cc) {
  def load = Action { implicit request =>
    Ok(views.html.version4Main())
  }
}