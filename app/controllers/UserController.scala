package controllers

import entities.Tables.{UserInfo, UserInfoRow}
import play.api._
import play.api.mvc._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.json._
import play.api.libs.json.Json
import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.api._

import javax.inject.Inject
import scala.concurrent.ExecutionContext

class UserController @Inject()(protected val dbConfigProvider: DatabaseConfigProvider, cc: ControllerComponents)(implicit ec: ExecutionContext)
    extends AbstractController(cc) with HasDatabaseConfigProvider[PostgresProfile] {
  case class UserInfoModel(userId: Option[Int], lastName: Option[String], firstName: Option[String], age: Option[Int])
  private implicit val recordReads: Reads[UserInfoModel] = Json.reads[UserInfoModel]
  private implicit val recordWrites: Writes[UserInfoModel] = Json.writes[UserInfoModel]

  def getUserAll: Action[AnyContent] = Action.async { implicit request =>
    val action = UserInfo.result
    db.run(action)
      .map(users => {
        Ok(Json.obj(
          "users" -> users.map(u => UserInfoModel(Some(u.userId), u.lastName, u.firstName, u.age))
        ))
      })
  }

  def postUser: Action[JsValue] = Action.async(parse.json) { implicit request =>
    val body = request.body.validate[UserInfoModel]
    body.map(data => {
      val action = (UserInfo returning UserInfo.map(_.userId) into ((u, id) => u.copy(userId = id))) +=
        UserInfoRow(0, data.lastName, data.firstName, data.age)
      db.run(action)
        .map(newUser => {
          Ok(Json.obj(
            "user" -> UserInfoModel(Some(newUser.userId), newUser.lastName, newUser.firstName, newUser.age)
          ))
        })
    }).get
  }
}
