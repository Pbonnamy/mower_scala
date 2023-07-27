package progfun

import play.api.libs.json.{JsNumber, JsObject, JsValue, Json}

object Exporter {

  def exportToCsv(mowers: List[Option[Mower]], filename: String): Unit = {
    val header =
      "numéro;début_x;début_y;début_direction;fin_x;fin_y;fin_direction;instructions"
    val lines = mowers.zipWithIndex.map {
      case (Some(mower), index) =>
        (index + 1).toString + ";" +
          mower.initialPosition.x.toString + ";" +
          mower.initialPosition.y.toString + ";" +
          mower.initialPosition.orientation.toString + ";" +
          mower.finalPosition.x.toString + ";" +
          mower.finalPosition.y.toString + ";" +
          mower.finalPosition.orientation.toString + ";" +
          mower.instructions
      case _ => ""
    }
    val csv = (header :: lines).mkString("\n")

    val writer = new java.io.PrintWriter(filename)
    writer.write(csv)
    writer.close()
  }

  def exportToJson(
      mowers: List[Option[Mower]],
      lawn: Lawn,
      filename: String): Unit = {
    val json: JsValue = Json.obj(
      "limite" -> Json.obj(
        "width"  -> JsNumber(lawn.width),
        "height" -> JsNumber(lawn.height)
      ),
      "tondeuses" -> mowers.zipWithIndex.map {
        case (Some(mower), _) =>
          Json.obj(
            "début" -> Json.obj(
              "point" -> Json.obj(
                "x" -> JsNumber(mower.initialPosition.x),
                "y" -> JsNumber(mower.initialPosition.y)
              ),
              "orientation" -> mower.initialPosition.orientation.toString
            ),
            "instructions" -> mower.instructions.split("").toList,
            "fin" -> Json.obj(
              "point" -> Json.obj(
                "x" -> JsNumber(mower.finalPosition.x),
                "y" -> JsNumber(mower.finalPosition.y)
              ),
              "orientation" -> mower.finalPosition.orientation.toString
            )
          )
        case _ => JsObject.empty
      }
    )

    val writer = new java.io.PrintWriter(filename)
    writer.write(Json.prettyPrint(json))
    writer.close()
  }

  def exportToYaml(
      mowers: List[Option[Mower]],
      lawn: Lawn,
      filename: String): Unit = {
    val yaml = {
      "limite:\n" +
        "  width: " + lawn.width.toString + "\n" +
        "  height: " + lawn.height.toString + "\n" +
        "tondeuses:\n" +
        mowers.zipWithIndex.map {
          case (Some(mower), _) =>
            "- début:\n" +
              "    point:\n" +
              "      x: " + mower.initialPosition.x.toString + "\n" +
              "      y: " + mower.initialPosition.y.toString + "\n" +
              "    orientation: " + mower.initialPosition.orientation.toString + "\n" +
              "  instructions: \n  - " + mower.instructions.mkString(
                "\n  - "
              ) + "\n" +
              "  fin:\n" +
              "    point:\n" +
              "      x: " + mower.finalPosition.x.toString + "\n" +
              "      y: " + mower.finalPosition.y.toString + "\n" +
              "    orientation: " + mower.finalPosition.orientation.toString + "\n"
          case _ => ""
        }.mkString
    }

    val writer = new java.io.PrintWriter(filename)
    writer.write(yaml)
    writer.close()
  }
}
