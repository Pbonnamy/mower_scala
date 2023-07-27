package Mower

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import progfun.{LawnMower, Orientation, Position}
import progfun.Lawn

class LawnMowerSpec extends AnyFlatSpec with Matchers {

  it should "turn left from the north" in {
    val initialPosition = Position(2, 3, Orientation.N)
    val finalPosition =
      LawnMower.processInstructions(initialPosition, "G", Lawn(5, 5))
    finalPosition should be(Position(2, 3, Orientation.W))
  }

  it should "turn left from the west" in {
    val initialPosition = Position(2, 3, Orientation.W)
    val finalPosition =
      LawnMower.processInstructions(initialPosition, "G", Lawn(5, 5))
    finalPosition should be(Position(2, 3, Orientation.S))
  }

  it should "turn right from the south" in {
    val initialPosition = Position(2, 3, Orientation.S)
    val finalPosition =
      LawnMower.processInstructions(initialPosition, "D", Lawn(5, 5))
    finalPosition should be(Position(2, 3, Orientation.W))
  }

  it should "turn right" in {
    val initialPosition = Position(1, 2, Orientation.W)
    val finalPosition =
      LawnMower.processInstructions(initialPosition, "D", Lawn(5, 5))
    finalPosition should be(Position(1, 2, Orientation.N))
  }

  it should "going forward to the east" in {
    val initialPosition = Position(3, 3, Orientation.E)
    val lawn = Lawn(5, 5)
    val finalPosition =
      LawnMower.processInstructions(initialPosition, "A", lawn)
    finalPosition should be(Position(4, 3, Orientation.E))
  }

  it should "going forward to the north" in {
    val initialPosition = Position(3, 3, Orientation.N)
    val lawn = Lawn(5, 5)
    val finalPosition =
      LawnMower.processInstructions(initialPosition, "A", lawn)
    finalPosition should be(Position(3, 4, Orientation.N))
  }

  it should "does nothing when the move is not possible" in {
    val initialPosition = Position(0, 0, Orientation.W)
    val lawn = Lawn(3, 3)
    val finalPosition =
      LawnMower.processInstructions(initialPosition, "A", lawn)
    finalPosition should be(Position(0, 0, Orientation.W))
  }

  it should "does nothing when wrong input" in {
    val initialPosition = Position(2, 2, Orientation.N)
    val lawn = Lawn(5, 5)
    val finalPosition =
      LawnMower.processInstructions(initialPosition, "X", lawn)
    finalPosition should be(Position(2, 2, Orientation.N))
  }

  it should "multiple valid inputs" in {
    val initialPosition = Position(2, 2, Orientation.N)
    val lawn = Lawn(5, 5)
    val finalPosition =
      LawnMower.processInstructions(initialPosition, "GAGAD", lawn)
    finalPosition should be(Position(1, 1, Orientation.W))
  }

  it should "multiple valid and invalids inputs" in {
    val initialPosition = Position(2, 2, Orientation.N)
    val lawn = Lawn(5, 5)
    val finalPosition =
      LawnMower.processInstructions(initialPosition, "GHAGKADL", lawn)
    finalPosition should be(Position(1, 1, Orientation.W))
  }

}
