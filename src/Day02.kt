import java.lang.IllegalArgumentException


fun String.toMoveCommand() = MoveCommand(this)
fun main() {

    fun part1(input: List<String>): Position {
        return input.map { it.toMoveCommand() }
            .fold(Position()) { pos, command ->
                pos.move(command)
            }
    }

    fun part2(input: List<String>): Position {
        return input.map { it.toMoveCommand() }
            .fold(PositionWithAim()) { pos, command ->
                pos.move(command)
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    println(part1(testInput))

    check(part1(testInput).product == 150)
    check(part2(testInput).product == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

open class Position(val depth: Int = 0, val horizontal: Int = 0) {

    val product = depth * horizontal

    open fun move(command: MoveCommand): Position =
        when (command.direction) {
            MoveCommand.Direction.FORWARD -> Position(depth, horizontal + command.amount)
            MoveCommand.Direction.DOWN -> Position(depth + command.amount, horizontal)
            MoveCommand.Direction.UP -> Position(depth - command.amount, horizontal)
        }

    override fun toString(): String {
        return "Position(depth=$depth, horizontal=$horizontal, product=$product)"
    }

}

class PositionWithAim(depth: Int = 0, horizontal: Int = 0, private val aim: Int = 0) : Position(depth, horizontal) {

    override fun move(command: MoveCommand): PositionWithAim =
        when (command.direction) {
            MoveCommand.Direction.FORWARD -> PositionWithAim(
                depth + aim * command.amount,
                horizontal + command.amount,
                aim
            )
            MoveCommand.Direction.DOWN -> PositionWithAim(depth, horizontal, aim + command.amount)
            MoveCommand.Direction.UP -> PositionWithAim(depth, horizontal, aim - command.amount)
        }

    override fun toString(): String {
        return "PositionWithAim(aim=$aim) ${super.toString()}"
    }


}

class MoveCommand(command: String) {

    val direction: Direction
    val amount: Int

    init {
        val split = command.split(' ', limit = 2)
        direction = Direction.fromString(split[0])
        amount = split[1].toInt()
    }


    enum class Direction {
        FORWARD,
        DOWN,
        UP;

        companion object {
            fun fromString(direction: String): Direction = when (direction) {
                "forward" -> FORWARD
                "down" -> DOWN
                "up" -> UP
                else -> throw IllegalArgumentException(direction)
            }
        }
    }

    override fun toString(): String {
        return "MoveCommand(direction=$direction, amount=$amount)"
    }

}