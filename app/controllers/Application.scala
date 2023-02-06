package controllers

import play.api.i18n._
import play.api.mvc._

import javax.inject._

@Singleton
class Application @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action {
    Ok(views.html.index())
  }
}
