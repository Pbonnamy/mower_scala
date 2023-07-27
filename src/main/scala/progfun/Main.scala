package progfun

import com.typesafe.config.{Config, ConfigFactory}

object Main extends App {
  val conf: Config = ConfigFactory.load()

  val lines = Parser.readFile(conf.getString("application.input-file")) match {
    case scala.util.Success(lines) => lines
    case scala.util.Failure(_) =>
      println("Error: could not read input file")
      sys.exit(1)
  }

  val lawn = Parser.parseLawn(lines.headOption.getOrElse("0 0")).getOrElse {
    println("Error: could not parse lawn")
    sys.exit(1)
  }

  val mowers = lines
    .drop(1)
    .grouped(2)
    .map {
      case List(position, instructions) =>
        Parser.parsePosition(position).map { position =>
          Mower(
            position,
            instructions,
            LawnMower.processInstructions(position, instructions, lawn)
          )
        }
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
