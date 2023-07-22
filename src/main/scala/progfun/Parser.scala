package progfun

object Parser {
  def parseLawn(line: String): Lawn = {
    val Array(width, height) = line.split(" ").map(_.toInt)
    Lawn(width, height)
  }

  def parsePosition(line: String): Position = {
    val Array(x, y, orientation) = line.split(" ")
    Position(x.toInt, y.toInt, Orientation.withName(orientation))
  }

  def readFile(filename: String): List[String] = {
    val bufferedSource = io.Source.fromFile(filename)
    val lines = (for (line <- bufferedSource.getLines()) yield line).toList
    bufferedSource.close
    lines
  }

}
