package progfun

import com.typesafe.config.{Config, ConfigFactory}

object Main extends App {
  val conf: Config = ConfigFactory.load()

  val lines = Parser.readFile(conf.getString("application.input-file"))

  val lawn = Parser.parseLawn(lines.headOption.getOrElse("0 0"))

  val mowers = lines
    .drop(1)
    .grouped(2)
    .map {
      case List(position, instructions) =>
        val finalPosition = LawnMower.processInstructions(
          Parser.parsePosition(position),
          instructions,
          lawn
        )
        Some(Mower(Parser.parsePosition(position), instructions, finalPosition))
      case _ =>
        None
    }
    .toList

  Exporter.exportToCsv(mowers, conf.getString("application.output-csv-file"))

  Exporter.exportToJson(
    mowers,
    lawn,
    conf.getString("application.output-json-file")
  )

  Exporter.exportToYaml(
    mowers,
    lawn,
    conf.getString("application.output-yaml-file")
  )
}
