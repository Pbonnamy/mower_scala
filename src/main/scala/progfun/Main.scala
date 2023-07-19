package progfun

object Main extends App {
  val lawn = Lawn(5, 5)

  val position = Position(0, 0, Orientation.N)

  val instructions = "GAGAGAGAA"

  val finalPosition =
    LawnMower.processInstructions(position, instructions, lawn)

  println("Final position: " + finalPosition.toString)
}
