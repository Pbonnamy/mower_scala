package fr.esgi.al.funprog

import com.typesafe.config.{Config, ConfigFactory}
import scala.util.{Failure, Success}

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

  val csv = Exporter.exportToCsv(mowers)

  Exporter.writeToFile(
    csv,
    conf.getString("application.output-csv-file")
  ) match {
    case Success(_) => println("CSV file written")
    case Failure(_) => println("Error: could not write CSV file")
  }

  val json = Exporter.exportToJson(mowers, lawn)

  Exporter.writeToFile(
    json,
    conf.getString("application.output-json-file")
  ) match {
    case Success(_) => println("JSON file written")
    case Failure(_) => println("Error: could not write JSON file")
  }

  val yaml = Exporter.exportToYaml(mowers, lawn)

  Exporter.writeToFile(
    yaml,
    conf.getString("application.output-yaml-file")
  ) match {
    case Success(_) => println("YAML file written")
    case Failure(_) => println("Error: could not write YAML file")
  }
}
