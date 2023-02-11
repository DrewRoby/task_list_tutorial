package controllers

import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import org.scalatestplus.play.{HtmlUnitFactory, OneBrowserPerSuite, PlaySpec}

class TaskList1TutorialSpec extends PlaySpec with GuiceOneServerPerSuite with OneBrowserPerSuite with HtmlUnitFactory {
  "Task list 1" must {
    "login and access functions" in {
      // "$port" vice "9000" because scalatest picks its own ports when it spins up a new server
      go to s"http://localhost:$port/login1"

      pageTitle mustBe "Login"
      find(cssSelector("h2")).isEmpty mustBe false
      find(cssSelector("h2")).foreach(e => e.text must include ("Login"))

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
  }
}