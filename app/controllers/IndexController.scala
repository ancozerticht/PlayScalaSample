package controllers

import play.api._
import play.api.mvc._

import javax.inject.Inject

class IndexController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index(): Action[AnyContent] = Action { implicit request =>
    Ok("Hello, Play!!!")
  }
}
