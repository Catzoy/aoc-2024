package utils

import java.io.File

fun readInput(day: String): List<String> {
    return File("src/main/kotlin/day$day/input.txt")
        .readLines()
}

val directions = listOf<Direction>(
    (-1 to 0),
    (1 to 0),
    (0 to -1),
    (0 to 1)
)
typealias Point = Pair<Int, Int>
typealias Direction = Pair<Int, Int>

operator fun Point.plus(other: Point): Point {
    return first + other.first to second + other.second
}

operator fun Point.minus(other: Point): Point {
    return first - other.first to second - other.second
}

val Direction.opposite
    get() = when (this) {
        (-1 to 0) -> (1 to 0)
        (1 to 0) -> (-1 to 0)
        (0 to -1) -> (0 to 1)
        (0 to 1) -> (0 to -1)
        else -> error("Invalid direction")
    }

val Direction.adjacent
    get() = when (this) {
        (-1 to 0), (1 to 0) -> listOf((0 to -1), (0 to 1))
        (0 to -1), (0 to 1) -> listOf((-1 to 0), (1 to 0))
        else -> error("Invalid direction")
    }