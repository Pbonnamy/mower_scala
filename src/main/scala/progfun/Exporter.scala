package progfun

import play.api.libs.json.{JsNumber, JsValue, Json}

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

    val csv = (header :: lines).filterNot(_.isEmpty).mkString("\n")

    writeToFile(csv, filename)
  }

  def exportToJson(
      mowers: List[Option[Mower]],
      lawn: Lawn,
      filename: String): Unit = {
    val json: JsValue = Json.obj(
      "limite" -> Json.obj(
        "x" -> JsNumber(lawn.width),
        "y" -> JsNumber(lawn.height)
      ),
      // dont write mowers if they are empty
      "tondeuses" -> mowers.zipWithIndex
        .filter(_._1.isDefined)
        .map {
          case (Some(mower), _) =>
            Json.obj(
              "debut" -> Json.obj(
                "point" -> Json.obj(
                  "x" -> JsNumber(mower.initialPosition.x),
                  "y" -> JsNumber(mower.initialPosition.y)
                ),
                "direction" -> mower.initialPosition.orientation.toString
              ),
              "instructions" -> mower.instructions.split("").toList,
              "fin" -> Json.obj(
                "point" -> Json.obj(
                  "x" -> JsNumber(mower.finalPosition.x),
                  "y" -> JsNumber(mower.finalPosition.y)
                ),
                "direction" -> mower.finalPosition.orientation.toString
              )
            )
          case _ => Json.obj()
        }
    )

    writeToFile(Json.prettyPrint(json), filename)
  }

  def exportToYaml(
      mowers: List[Option[Mower]],
      lawn: Lawn,
      filename: String): Unit = {
    val yaml = {
      "limite:\n" +
        "  x: " + lawn.width.toString + "\n" +
        "  y: " + lawn.height.toString + "\n" +
        "tondeuses:\n" +
        mowers.zipWithIndex.map {
          case (Some(mower), _) =>
            "- debut:\n" +
              "    point:\n" +
              "      x: " + mower.initialPosition.x.toString + "\n" +
              "      y: " + mower.initialPosition.y.toString + "\n" +
              "    direction: " + mower.initialPosition.orientation.toString + "\n" +
              "  instructions: \n  - " + mower.instructions.mkString(
                "\n  - "
              ) + "\n" +
              "  fin:\n" +
              "    point:\n" +
              "      x: " + mower.finalPosition.x.toString + "\n" +
              "      y: " + mower.finalPosition.y.toString + "\n" +
              "    direction: " + mower.finalPosition.orientation.toString + "\n"
          case _ => ""
        }.mkString
    }

    writeToFile(yaml, filename)
  }

  private def writeToFile(content: String, filename: String): Unit = {
    val writer = new java.io.PrintWriter(filename)
    writer.write(content)
    writer.close()
  }
}
