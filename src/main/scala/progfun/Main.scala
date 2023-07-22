package progfun

import com.typesafe.config.{Config, ConfigFactory}

object Main extends App {
  val conf: Config = ConfigFactory.load()

  val lines = Parser.readFile(conf.getString("application.input-file"))

  val lawn = Parser.parseLawn(lines.headOption.getOrElse("0 0"))

  lines.drop(1).grouped(2).foreach {
    case List(position, instructions) =>
      val finalPosition = LawnMower.processInstructions(
        Parser.parsePosition(position),
        instructions,
        lawn
      )
      println("Position finale: " + finalPosition.toString)
    case _ => println("Erreur de parsing")
  }
}
