package models

import play.api.libs.json.Json

case class TaskItem(id: Int, text: String)
case class UserData(username: String, password: String)

object UserData {
  implicit val userDataReads = Json.reads[UserData]
  implicit val userDataWrites = Json.writes[UserData]
}