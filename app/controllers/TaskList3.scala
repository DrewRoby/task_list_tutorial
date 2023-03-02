package controllers

import models.{TaskListInMemoryModel, UserData}
import play.api.libs.json.{JsError, JsSuccess, Json, Reads}
import play.api.mvc._

import javax.inject._


@Singleton
class TaskList3 @Inject() (cc: ControllerComponents) extends AbstractController(cc) {
  def load = Action { implicit request =>
    Ok(views.html.version3Main())
  }

  //This is probably not the "best" place to implement a json reader, nor is this the only way to do it (see play documentation on building readers and writers)
  //It is implemented here for the sake of brevity, and because it's not wrong as such
  implicit val userDataReads = Json.reads[UserData]
  //What I DON'T understand is how the black magic of this implicit works with "fromJson[UserData]" in validate

  def withJsonBody[A](f: A => Result)(implicit request: Request[AnyContent], reads: Reads[A]) = {
    request.body.asJson.map { body =>
      Json.fromJson[A](body) match {
        case JsSuccess(a, path) => f(a)
        case e @ JsError(_) => Redirect(routes.TaskList3.load)
      }
    }.getOrElse(Redirect(routes.TaskList3.load))
  }

  def validate = Action { implicit request =>
    withJsonBody[UserData] { ud =>
      if (TaskListInMemoryModel.validateUser(ud.username, ud.password)) {
        Ok(Json.toJson(true))
          .withSession("username" -> ud.username, "csrfToken" -> play.filters.csrf.CSRF.getToken.get.value)
      } else {
        Ok(Json.toJson(false))
      }
    }
  }


  def taskList = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      Ok(Json.toJson(TaskListInMemoryModel.getTasks(username)))
    }.getOrElse(Ok(Json.toJson(Seq.empty[String])))
  }

  def addTask = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      request.body.asJson.map { body =>
        Json.fromJson[String](body) match {
          case JsSuccess(task, path) =>
            TaskListInMemoryModel.addTask(username, task);
            Ok(Json.toJson(true))
          case e@JsError(_) => Redirect(routes.TaskList3.load)
        }
      }.getOrElse(Ok(Json.toJson(false)))
    }.getOrElse(Ok(Json.toJson(false)))
  }

  def delete = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      request.body.asJson.map { body =>
        Json.fromJson[Int](body) match {
          case JsSuccess(index, path) =>
            TaskListInMemoryModel.removeTask(username, index)
            Ok(Json.toJson(true))
          case e@JsError(_) => Redirect(routes.TaskList3.load)
        }
      }.getOrElse(Ok(Json.toJson(false)))
    }.getOrElse(Ok(Json.toJson(false)))

  }

  def logout = Action {
    Ok(Json.toJson(true)).withNewSession
  }

  def createUser = Action { implicit request =>
    request.body.asJson.map { body =>
      Json.fromJson[UserData](body) match {
        case JsSuccess(ud, path) =>
          if (TaskListInMemoryModel.createUser(ud.username, ud.password)) {
            Ok(Json.toJson(true))
              .withSession("username" -> ud.username, "csrfToken" -> play.filters.csrf.CSRF.getToken.get.value)
          } else {
            Ok(Json.toJson(false))
          }
        case e@JsError(_) => Redirect(routes.TaskList3.load)
      }
    }.getOrElse(Redirect(routes.TaskList3.load))
  }

}