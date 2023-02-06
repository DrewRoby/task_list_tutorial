package controllers

import play.api.i18n._
import play.api.mvc._

import javax.inject._

@Singleton
class TaskList1 @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  def taskList = Action {
    val tasks = List("task1","task2","task3","sleep","eat")
    Ok(views.html.taskList1(tasks))
  }

  def product(prodType: String, prodNum: Int) = Action {
    Ok(s"Product type is : $prodType, product number is: $prodNum")
  }

}
