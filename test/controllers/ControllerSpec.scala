package controllers

import org.scalatestplus.play.PlaySpec
import play.api.test.Helpers._
import play.api.test.{FakeRequest, Helpers}

class ControllerSpec extends PlaySpec {
  "Application#index" must {
    "give back expected page" in {
      val controller = new Application(Helpers.stubControllerComponents())
      val result = controller.index.apply(FakeRequest())
      val bodyText = contentAsString(result)
      bodyText must include ("Welcome to Play!")
    }
  }

}
