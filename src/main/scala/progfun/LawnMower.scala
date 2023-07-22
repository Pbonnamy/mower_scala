package progfun

object LawnMower {

  private def turnLeft(position: Position): Position =
    position.orientation match {
      case Orientation.N => position.copy(orientation = Orientation.W)
      case Orientation.E => position.copy(orientation = Orientation.N)
      case Orientation.S => position.copy(orientation = Orientation.E)
      case Orientation.W => position.copy(orientation = Orientation.S)
      case _             => position
    }

  private def turnRight(position: Position): Position =
    position.orientation match {
      case Orientation.N => position.copy(orientation = Orientation.E)
      case Orientation.E => position.copy(orientation = Orientation.S)
      case Orientation.S => position.copy(orientation = Orientation.W)
      case Orientation.W => position.copy(orientation = Orientation.N)
      case _             => position
    }

  private def moveForward(position: Position, lawn: Lawn): Position =
    position.orientation match {
      case Orientation.N =>
        if (position.y + 1 > lawn.height) position
        else position.copy(y = position.y + 1)
      case Orientation.E =>
        if (position.x + 1 > lawn.width) position
        else position.copy(x = position.x + 1)
      case Orientation.S =>
        if (position.y - 1 < 0) position else position.copy(y = position.y - 1)
      case Orientation.W =>
        if (position.x - 1 < 0) position else position.copy(x = position.x - 1)
      case _ => position
    }

  def processInstructions(
      position: Position,
      instructions: String,
      lawn: Lawn): Position = {
    instructions.foldLeft(position)((pos, instruction) =>
      instruction match {
        case 'G' => turnLeft(pos)
        case 'D' => turnRight(pos)
        case 'A' => moveForward(pos, lawn)
        case _   => pos
      }
    )
  }

}
