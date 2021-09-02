package controllers

import entities.Tables.UserInfo
import models.User
import play.api._
import play.api.mvc._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.api._
import views.html.UserProfile

import javax.inject.Inject
import scala.concurrent.ExecutionContext

class MyPageController @Inject()(protected val dbConfigProvider: DatabaseConfigProvider, cc: ControllerComponents)(implicit ec: ExecutionContext)
  extends AbstractController(cc) with HasDatabaseConfigProvider[PostgresProfile] {

  def getUserProfilePage(id: Int): Action[AnyContent] = Action.async { implicit request =>
    val action = UserInfo.filter(_.userId === id).result.head
    db.run(action)
      .map(userEntity => {
        val user = User(userEntity.userId, userEntity.firstName, userEntity.lastName, userEntity.age)
        Ok(UserProfile.render(user))
      })
  }
}
