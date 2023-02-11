package controllers

import play.api.i18n._
import play.api.mvc._

import javax.inject._

@Singleton
class Application @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action { implicit request =>
    Ok(views.html.index())
  }

   def product(prodType: String, prodNum: Int) = Action { implicit request =>
     Ok{s"Product type is: $prodType, product number is: $prodNum"}
   }
}
