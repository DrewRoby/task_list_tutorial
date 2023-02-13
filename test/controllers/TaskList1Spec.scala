package controllers

import org.scalatest.concurrent.Eventually.eventually
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import org.scalatestplus.selenium.Chrome._
import play.api.test.CSRFTokenHelper._
import play.api.test.Helpers._
import play.api.test._

//import com.marklewis.app.controllers.TaskList1.USER_CREATION_FAILED_ERROR  //How do I import a value from one of my controllers?  Or should that go somewhere else that both this script and the controller can acces it, and where is that?

class TaskList1Spec extends PlaySpec with GuiceOneServerPerSuite with Injecting {
  val USER_CREATION_FAILED_ERROR = "User creation failed."

  "The TaskList1 controller" should {

    "not allow a user to login with a GET request" in {
      val controller = new TaskList1(stubMessagesControllerComponents())
      val loginGet = controller.validateLoginGet("Mark","pass").apply(FakeRequest(GET, "/validateGet1"))

      status(loginGet) must not be OK
    }

    "allow a user to login with a POST request" in {
      go to s"http://localhost:$port/login1"
      click on "username-login"
      textField("username-login").value = "Mark"
      click on "password-login"
      pwdField("password-login").value = "pass"
      submit()
      eventually {
        pageTitle mustBe "Task List"
        find(cssSelector("h2")).isEmpty mustBe false
        find(cssSelector("h2")).foreach(e => e.text mustBe "TaskList")
        findAll(cssSelector("li")).toList.map(_.text) mustBe List("Make videos","eat","sleep","code")
      }
    }


    "not create an invalid user" in {
      go to s"http://localhost:$port/login1"
      click on "username-create"
      textField("username-create").value = "Bo"
      click on "password-create"
      pwdField("password-create").value = "password"
      submit()
      eventually {
        find(USER_CREATION_FAILED_ERROR) mustBe true
      }
    }

    "not allow the same username twice" in {
      go to s"http://localhost:$port/login1"
      click on "username-create"
      textField("username-create").value = "Mark"
      click on "password-login"
      pwdField("password-create").value = "password"
      submit()
      eventually {
        find(USER_CREATION_FAILED_ERROR) mustBe true
      }

    }


    "create a new user, add, and delete tasks" in {
      go to s"http://localhost:$port/login1"
      click on "username-create"
      textField("username-create").value = "Jonesey"
      click on "password-login"
      pwdField("password-create").value = "password"

    }


    //    "not allow a user to create a username of less than three characters" in {
//      val controller = new TaskList1(stubMessagesControllerComponents())
//      val home = controller.createUserForm("username"=>"bo")
//    }
//    "not allow a user to create a username of more than ten characters"
//    "not allow a user to create a password of less than eight characters"

  }
}

