package progfun

import org.scalatest.flatspec.AnyFlatSpec
import progfun.{Lawn, Orientation, Parser, Position}

class ParserSpec extends AnyFlatSpec {

  it should "parse a lawn" in {
    val line = "5 5"
    val expectedLawn = Lawn(5, 5)
    val parsedLawn = Parser.parseLawn(line)
    assert(parsedLawn.contains(expectedLawn))
  }

  it should "not parse a lawn" in {
    val lawn = Lawn(5, 5)
    val lines = "kjbnjkbmljb"
    val expectedLines = lawn
    val parsedLawn = Parser.parseLawn(lines)
    assert(!parsedLawn.contains(expectedLines))
  }

  it should "parse a position" in {
    val expectedPosition = Position(4, 5, Orientation.N)
    val parsedPosition = Parser.parsePosition("4 5 N")
    assert(parsedPosition.contains(expectedPosition))
  }

  it should "not parse a position" in {
    val expectedPosition = Position(4, 5, Orientation.N)
    val parsedPosition = Parser.parsePosition("77 22 K")
    assert(!parsedPosition.contains(expectedPosition))
  }

  it should "read a file" in {
    val lines = Parser.readFile("src/test/resources/test.txt")
    assert(lines.isSuccess)
  }

}
