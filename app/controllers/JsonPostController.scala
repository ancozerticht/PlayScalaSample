package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._

import javax.inject.Inject

class JsonPostController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  case class RequestRecordModel(id: Int, name: String)
  private implicit val recordReads: Reads[RequestRecordModel] = Json.reads[RequestRecordModel]

  def post(): Action[JsValue] = Action(parse.json) { implicit request =>
    val body = request.body.validate[RequestRecordModel]
    body.fold(
      details => {
        BadRequest(Json.obj(
          "reason" -> "Validation Error",
          "details" -> details.map { case (path, errors) => Json.obj(
            "path" -> path.toString(),
            "errors" -> JsArray(errors.map { error => Json.obj(
              "messages" -> JsArray(error.messages.map { Json.toJson(_) }),
              "args" -> JsArray(error.args.map { arg => Json.toJson(arg.toString()) })
            ) })
          )}
        ))
      },
      body => {
        Ok(Json.obj())
      }
    )
  }
}
