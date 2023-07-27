package progfun

import scala.util.Try

object Parser {
  def parseLawn(line: String): Option[Lawn] = {
    line.split(" ").toList match {
      case width :: height :: Nil =>
        if (width.toInt > 0 && height.toInt > 0)
          Some(Lawn(width.toInt, height.toInt))
        else None
      case _ => None
    }
  }

  def parsePosition(line: String): Option[Position] = {
    line.split(" ").toList match {
      case x :: y :: orientation :: Nil =>
        if (
          x.toInt >= 0 && y.toInt >= 0 && Orientation.values
            .map(_.toString)
            .contains(orientation)
        )
          Some(Position(x.toInt, y.toInt, Orientation.withName(orientation)))
        else None
      case _ => None
    }
  }

  def readFile(filename: String): Try[List[String]] = {
    Try {
      val source = scala.io.Source.fromFile(filename)
      val lines = source.getLines().toList
      source.close()
      lines
    }
  }
}
