package progfun

import fr.esgi.al.funprog.{Lawn, LawnMower, Orientation, Position}
import org.scalatest.matchers.should.Matchers
import org.scalatest.funsuite.AnyFunSuite

class LawnMowerSpec extends AnyFunSuite with Matchers {

  test("turn left from the north") {
    val initialPosition = Position(2, 3, Orientation.N)
    val finalPosition =
      LawnMower.processInstructions(initialPosition, "G", Lawn(5, 5))
    finalPosition should be(Position(2, 3, Orientation.W))
  }

  test("turn left from the west") {
    val initialPosition = Position(2, 3, Orientation.W)
    val finalPosition =
      LawnMower.processInstructions(initialPosition, "G", Lawn(5, 5))
    finalPosition should be(Position(2, 3, Orientation.S))
  }

  test("turn right from the south") {
    val initialPosition = Position(2, 3, Orientation.S)
    val finalPosition =
      LawnMower.processInstructions(initialPosition, "D", Lawn(5, 5))
    finalPosition should be(Position(2, 3, Orientation.W))
  }

  test("turn right") {
    val initialPosition = Position(1, 2, Orientation.W)
    val finalPosition =
      LawnMower.processInstructions(initialPosition, "D", Lawn(5, 5))
    finalPosition should be(Position(1, 2, Orientation.N))
  }

  test("going forward to the east") {
    val initialPosition = Position(3, 3, Orientation.E)
    val lawn = Lawn(5, 5)
    val finalPosition =
      LawnMower.processInstructions(initialPosition, "A", lawn)
    finalPosition should be(Position(4, 3, Orientation.E))
  }

  test("going forward to the north") {
    val initialPosition = Position(3, 3, Orientation.N)
    val lawn = Lawn(5, 5)
    val finalPosition =
      LawnMower.processInstructions(initialPosition, "A", lawn)
    finalPosition should be(Position(3, 4, Orientation.N))
  }

  test("does nothing when the move is not possible") {
    val initialPosition = Position(0, 0, Orientation.W)
    val lawn = Lawn(3, 3)
    val finalPosition =
      LawnMower.processInstructions(initialPosition, "A", lawn)
    finalPosition should be(Position(0, 0, Orientation.W))
  }

  test("does nothing when wrong input") {
    val initialPosition = Position(2, 2, Orientation.N)
    val lawn = Lawn(5, 5)
    val finalPosition =
      LawnMower.processInstructions(initialPosition, "X", lawn)
    finalPosition should be(Position(2, 2, Orientation.N))
  }

  test("multiple valid inputs") {
    val initialPosition = Position(2, 2, Orientation.N)
    val lawn = Lawn(5, 5)
    val finalPosition =
      LawnMower.processInstructions(initialPosition, "GAGAD", lawn)
    finalPosition should be(Position(1, 1, Orientation.W))
  }

  test("multiple valid and invalids inputs") {
    val initialPosition = Position(2, 2, Orientation.N)
    val lawn = Lawn(5, 5)
    val finalPosition =
      LawnMower.processInstructions(initialPosition, "GHAGKADL", lawn)
    finalPosition should be(Position(1, 1, Orientation.W))
  }

}
