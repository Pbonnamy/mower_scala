package progfun

import org.scalatest.flatspec.AnyFlatSpec
import progfun.{Lawn, Orientation, Parser, Position}

class LawnSpec extends AnyFlatSpec {

  it should "parse a lawn" in {
    val lines = "5 5"
    val expectedLines = Lawn(5, 5)
    val parsedLawn = Parser.parseLawn(lines)
    assert(parsedLawn == expectedLines)
  }

  /*it should "parse a lawn" in {
    val lawn = Lawn(5, 5)
    val lines = "kjbnjkbmljb"
    val expectedLines = lawn
    val parsedLawn = Parser.parseLawn(lines)
    assert(parsedLawn == expectedLines)
  }*/


  it should "parse a position" in {
    val expectedPosition = Position(4, 5, Orientation.N)
    val parsedPosition = Parser.parsePosition("4 5 N")
    assert(expectedPosition == parsedPosition)
  }

  it should "read a file" in {
    val lines = Parser.readFile("src/test/resources/test.txt")
    assert(lines == List("azertyuiuop", "azertyuiopqsdfghjklmwxcvbn"))
  }

}
