package day21

import utils.Point
import utils.minus
import utils.readInput
import utils.toChar

fun List<List<Char>>.positionOf(char: Char): Point {
    for (y in indices) {
        for (x in this[y].indices) {
            if (this[y][x] == char) {
                return Point(y, x)
            }
        }
    }
    throw Exception("Unknown char $char")
}

fun typePresses(
    keyboard: List<List<Char>>,
    moves: Map<Point, List<List<Point>>>,
    password: String,
): String {
    var position = keyboard.positionOf('A')
    val presses = StringBuffer()
    for (char in password) {
        val next = keyboard.positionOf(char)
        if (position != next) {
            val relative = moves.getValue(position).first { it.last() == next }
            val actions = sequence { yield(position); yieldAll(relative) }
                .zipWithNext()
                .map { (a, b) -> b - a }
                .map { dir -> dir.toChar() }
                .joinToString("")
            presses.append(actions)
        }
        presses.append("A")
        position = next
    }
    return presses.toString()
}

fun main() {
    val numericKeyboard = listOf(
        listOf('7', '8', '9'),
        listOf('4', '5', '6'),
        listOf('1', '2', '3'),
        listOf('X', '0', 'A'),
    )
    val numericMoves = mapOf(
        // 7
        (0 to 0) to listOf(
            listOf((0 to 1)), // 8
            listOf((0 to 1), (0 to 2)), // 9
            listOf((0 to 1), (0 to 2), (1 to 2)), // 6
            listOf((0 to 1), (0 to 2), (1 to 2), (2 to 2)), // 3
            listOf((0 to 1), (0 to 2), (1 to 2), (2 to 2), (3 to 2)), // A
            listOf((0 to 1), (1 to 1)), // 5
            listOf((0 to 1), (1 to 1), (2 to 1)), // 2
            listOf((0 to 1), (1 to 1), (2 to 1), (3 to 1)), // 0
            listOf((1 to 0)), // 4
            listOf((1 to 0), (2 to 0)), // 1
        ),
        // 8
        (0 to 1) to listOf(
            listOf((0 to 0)), // 7
            listOf((0 to 0), (1 to 0)), // 4
            listOf((0 to 0), (1 to 0), (2 to 0)), // 1
            listOf((1 to 1)), // 5
            listOf((1 to 1), (2 to 1)), // 2
            listOf((1 to 1), (2 to 1), (3 to 1)), // 0
            listOf((0 to 2)), // 9
            listOf((0 to 2), (1 to 2)), // 6
            listOf((1 to 1), (2 to 1), (2 to 2)), // 3
            listOf((0 to 2), (1 to 2), (2 to 2), (3 to 2)), // A
        ),
        // 9
        (0 to 2) to listOf(
            listOf((1 to 2)),
            listOf((1 to 2), (2 to 2)),
            listOf((1 to 2), (2 to 2), (3 to 2)),
            listOf((0 to 1)),
            listOf((0 to 1), (1 to 1)),
            listOf((0 to 1), (1 to 1), (2 to 1)),
            listOf((0 to 1), (1 to 1), (2 to 1), (3 to 1)),
            listOf((0 to 1), (0 to 0)),
            listOf((0 to 1), (0 to 0), (1 to 0)),
            listOf((0 to 1), (0 to 0), (1 to 0), (2 to 0)),
        ),
        // 4
        (1 to 0) to listOf(
            listOf((0 to 0)),
            listOf((0 to 0), (0 to 1)),
            listOf((0 to 0), (0 to 1), (0 to 2)),
            listOf((1 to 1)),
            listOf((1 to 1), (1 to 2)),
            listOf((1 to 1), (1 to 2), (2 to 2)),
            listOf((1 to 1), (1 to 2), (2 to 2), (3 to 2)),
            listOf((1 to 1), (2 to 1)),
            listOf((1 to 1), (2 to 1), (3 to 1)),
            listOf((2 to 0)),
        ),
        // 5
        (1 to 1) to listOf(
            listOf((0 to 1)), // 8
            listOf((0 to 1), (0 to 0)), // 7
            listOf((0 to 1), (0 to 2)), // 9
            listOf((1 to 2)), // 6
            listOf((1 to 0)), // 4
            listOf((1 to 0), (2 to 0)), // 1
            listOf((2 to 1)), // 2
            listOf((2 to 1), (2 to 2)), // 3
            listOf((2 to 1), (3 to 1)), // 0
            listOf((2 to 1), (3 to 1), (3 to 2)), // A
        ),
        // 6
        (1 to 2) to listOf(
            listOf((0 to 2)), // 9
            listOf((1 to 1)), // 5
            listOf((1 to 1), (0 to 1)), // 8
            listOf((1 to 1), (1 to 0)), // 4
            listOf((1 to 1), (1 to 0), (0 to 0)), // 7
            listOf((1 to 1), (1 to 0), (2 to 0)), // 1
            listOf((1 to 1), (2 to 1)), // 2
            listOf((1 to 1), (2 to 1), (3 to 1)), // 0
            listOf((2 to 2)), // 3
            listOf((2 to 2), (3 to 2)), // A
        ),
        // 1
        (2 to 0) to listOf(
            listOf((1 to 0)),
            listOf((1 to 0), (1 to 1)),
            listOf((1 to 0), (1 to 1), (1 to 2)),
            listOf((1 to 0), (0 to 0)),
            listOf((1 to 0), (0 to 0), (0 to 1)),
            listOf((1 to 0), (0 to 0), (0 to 1), (0 to 2)),
            listOf((2 to 1)),
            listOf((2 to 1), (2 to 2)),
            listOf((2 to 1), (2 to 2), (3 to 2)), // A
            listOf((2 to 1), (3 to 1)),
        ),
        // 2
        (2 to 1) to listOf(
            listOf((3 to 1)), // 0
            listOf((3 to 1), (3 to 2)), // A
            listOf((2 to 2)), // 3
            listOf((2 to 2), (1 to 2)), // 6
//            listOf((1 to 1), (1 to 2)), // 6 - option 2
            listOf((2 to 2), (1 to 2), (0 to 2)),
            listOf((1 to 1)),
            listOf((1 to 1), (0 to 1)),
            listOf((2 to 0)),
            listOf((2 to 0), (1 to 0)),
            listOf((2 to 0), (1 to 0), (0 to 0)),
        ),
        // 3
        (2 to 2) to listOf(
            listOf((3 to 2)),
            listOf((1 to 2)),
            listOf((1 to 2), (0 to 2)),
            listOf((2 to 1)),
            listOf((2 to 1), (3 to 1)),
            listOf((2 to 1), (1 to 1)),
            listOf((2 to 1), (1 to 1), (0 to 1)),
            listOf((2 to 1), (2 to 0)),
            listOf((2 to 1), (2 to 0), (1 to 0)),
            listOf((2 to 1), (2 to 0), (1 to 0), (0 to 0)),
        ),
        // 0
        (3 to 1) to listOf(
            listOf((3 to 2)),
            listOf((2 to 1)),
            listOf((2 to 1), (2 to 2)),
            listOf((2 to 1), (2 to 0)),
            listOf((2 to 1), (2 to 0), (1 to 0)),
            listOf((2 to 1), (2 to 0), (1 to 0), (0 to 0)),
            listOf((2 to 1), (1 to 1)),
            listOf((2 to 1), (1 to 1), (1 to 2)),
            listOf((2 to 1), (1 to 1), (0 to 1)),
            listOf((2 to 1), (1 to 1), (0 to 1), (0 to 2)),
        ),
        // A
        (3 to 2) to listOf(
            listOf((3 to 1)), // 0
            listOf((2 to 2)), // 3
            listOf((3 to 1), (2 to 1)), // 2
            listOf((2 to 2), (2 to 1), (2 to 0)), // 1
            listOf((2 to 2), (1 to 2)), // 6
            listOf((2 to 2), (1 to 2), (1 to 1)), // 5
            listOf((2 to 2), (1 to 2), (1 to 1), (1 to 0)), // 4
            listOf((2 to 2), (1 to 2), (0 to 2)), // 9
            listOf((3 to 1), (2 to 1), (1 to 1), (0 to 1)), // 8
            listOf((2 to 2), (1 to 2), (0 to 2), (0 to 1), (0 to 0)), // 7
        ),
    )
    val directionalKeyboard = listOf(
        listOf('X', '^', 'A'),
        listOf('<', 'v', '>'),
    )
    val directionalMoves = mapOf(
        // A
        (0 to 2) to listOf(
            listOf((0 to 1)),
            listOf((1 to 2)),
            listOf((1 to 2), (1 to 1)),
            listOf((1 to 2), (1 to 1), (1 to 0)),
        ),
        // ^
        (0 to 1) to listOf(
            listOf((0 to 2)),
            listOf((1 to 1)),
            listOf((1 to 1), (1 to 2)),
            listOf((1 to 1), (1 to 0)),
        ),
        // <
        (1 to 0) to listOf(
            listOf((1 to 1)),
            listOf((1 to 1), (0 to 1)),
            listOf((1 to 1), (1 to 2)),
            listOf((1 to 1), (1 to 2), (0 to 2)),
        ),
        // v
        (1 to 1) to listOf(
            listOf((0 to 1)),
            listOf((0 to 1), (0 to 2)),
            listOf((1 to 0)),
            listOf((1 to 2)),
        ),
        // >
        (1 to 2) to listOf(
            listOf((0 to 2)),
            listOf((1 to 1)),
            listOf((1 to 1), (0 to 1)),
            listOf((1 to 1), (1 to 0)),
        )
    )
    val passwords = readInput("21")
    var sum = 0
    for (password in passwords) {
        println("Password: $password: ")
        val directional = typePresses(
            keyboard = numericKeyboard,
            moves = numericMoves,
            password = password,
        )
        println("STEP 1: $directional")
        val directional2 = typePresses(
            keyboard = directionalKeyboard,
            moves = directionalMoves,
            password = directional,
        )
        println("STEP 2: $directional2")
        val directional3 = typePresses(
            keyboard = directionalKeyboard,
            moves = directionalMoves,
            password = directional2,
        )
        println("STEP 3: $directional3")
        val complexity = directional3.length
        val nPart = password.dropLast(1).toInt()
        println("$complexity * $nPart")
        sum += complexity * nPart
        println("S: $sum")
    }
    println(sum)
}