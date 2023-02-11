package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.Play.materializer
import play.api.test.CSRFTokenHelper._
import play.api.test.Helpers._
import play.api.test._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 *
 * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
 */
class TaskList1Spec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "The TaskList1 controller" should {

    "not allow a user to login with a GET request" in {
      val controller = new TaskList1(stubMessagesControllerComponents())
      val loginGet = controller.validateLoginGet("Mark","pass").apply(FakeRequest(GET, "/validateGet1"))

      status(loginGet) must not be OK
    }

    "allow a user to login with a POST request" in {
      val controller = new TaskList1(stubMessagesControllerComponents())
      // I know I need to introduce the username and password here, but I don't know how
      // Probably has something to do with getting them into the FakeRequest body, but it's unclear how I'd do that
      val body = ???
      val home = controller.validateLoginPost.apply(FakeRequest(POST, "/validatePost1").withFormUrlEncodedBody(body).withCSRFToken)

      status(home) mustBe OK //Returns a 303 - SEE_OTHER?
    }

//    "not allow a user to create a username of less than three characters" in {
//      val controller = new TaskList1(stubMessagesControllerComponents())
//      val home = controller.createUserForm("username"=>"bo")
//    }
//    "not allow a user to create a username of more than ten characters"
//    "not allow a user to create a password of less than eight characters"

  }
}

