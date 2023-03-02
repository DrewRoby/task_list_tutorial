package controllers

import actors.{ChatActor, ChatManager}
import akka.actor.{ActorSystem, Props}
import akka.stream.Materializer
import play.api.libs.streams.ActorFlow
import play.api.mvc.{AbstractController, ControllerComponents, WebSocket}

import javax.inject._

@Singleton
class WebSocketChat @Inject() (cc: ControllerComponents)(implicit system: ActorSystem, mat: Materializer) extends AbstractController(cc) {
  val manager = system.actorOf(Props[ChatManager], "Manager")

  def index = Action { implicit request =>
    Ok(views.html.chatPage())
  }

  def socket = WebSocket.accept[String,String] { request =>
    println("Getting socket")
    ActorFlow.actorRef { out =>
      ChatActor.props(out, manager)
    }
  }

}
