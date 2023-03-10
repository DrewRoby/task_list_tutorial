package controllers

import models.TaskListInMemoryModel
import play.api.data._
import play.api.i18n._
import play.api.mvc._

import javax.inject._

@Singleton
class TaskList2 @Inject() (cc: ControllerComponents) extends AbstractController(cc) {
  def load = Action { implicit request =>
    Ok(views.html.version2Main())
  }

  def login = Action {
    Ok(views.html.login2())
  }

  def validate(username: String, password: String) = Action {
    if (TaskListInMemoryModel.validateUser(username, password)) {
      Ok(views.html.taskList2(TaskListInMemoryModel.getTasks(username))).withSession("username" -> username)
    } else {
      Ok(views.html.login2())
    }

  }

  def createUser(username: String, password: String) = Action {
    if (TaskListInMemoryModel.createUser(username, password)) {
      Ok(views.html.taskList2(TaskListInMemoryModel.getTasks(username))).withSession("username" -> username)
    } else {
      Ok(views.html.login2())
    }
  }

  def delete(index: Int) = Action { implicit request =>
    Ok("deleting")
  }

}
