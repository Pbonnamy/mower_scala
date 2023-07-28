package progfun

import fr.esgi.al.funprog.{Lawn, Orientation, Parser, Position}
import org.scalatest.funsuite.AnyFunSuite

class ParserSpec extends AnyFunSuite {

  test("parse a lawn") {
    val line = "5 5"
    val expectedLawn = Lawn(5, 5)
    val parsedLawn = Parser.parseLawn(line)
    assert(parsedLawn.contains(expectedLawn))
  }

  test("not parse a lawn") {
    val lawn = Lawn(5, 5)
    val lines = "kjbnjkbmljb"
    val expectedLines = lawn
    val parsedLawn = Parser.parseLawn(lines)
    assert(!parsedLawn.contains(expectedLines))
  }

  test("parse a position") {
    val expectedPosition = Position(4, 5, Orientation.N)
    val parsedPosition = Parser.parsePosition("4 5 N")
    assert(parsedPosition.contains(expectedPosition))
  }

  test("not parse a position") {
    val expectedPosition = Position(4, 5, Orientation.N)
    val parsedPosition = Parser.parsePosition("77 22 K")
    assert(!parsedPosition.contains(expectedPosition))
  }

  test("read a file") {
    val lines = Parser.readFile("src/test/resources/test.txt")
    assert(lines.isSuccess)
  }

}
