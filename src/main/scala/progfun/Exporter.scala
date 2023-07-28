package progfun

import play.api.libs.json.{JsNumber, JsValue, Json}
import scala.util.Try

object Exporter {

  def exportToCsv(mowers: List[Option[Mower]]): String = {
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

    csv
  }

  def exportToJson(mowers: List[Option[Mower]], lawn: Lawn): String = {
    val json: JsValue = Json.obj(
      "limite" -> Json.obj(
        "x" -> JsNumber(lawn.width),
        "y" -> JsNumber(lawn.height)
      ),
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

    Json.prettyPrint(json)
  }

  def exportToYaml(mowers: List[Option[Mower]], lawn: Lawn): String = {
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

    yaml
  }

  def writeToFile(content: String, filename: String): Try[Unit] = {
    Try {
      val writer = new java.io.PrintWriter(filename)
      writer.write(content)
      writer.close()
    }
  }
}
