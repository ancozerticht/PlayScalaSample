package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._

import javax.inject.Inject

class JsonController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  case class ResponseRecordModel(id: Int, name: String)
  private implicit val recordWrites = Json.writes[ResponseRecordModel]

  def json(page: Int, limit: Int): Action[AnyContent] = Action { implicit request =>
    val start = (page - 1) * limit
    val records = (start until limit + start).map(n => ResponseRecordModel(n, "name" + n.toString))
    Ok(Json.obj(
      "status" -> 0,
      "records" -> records
    ))
  }
}
