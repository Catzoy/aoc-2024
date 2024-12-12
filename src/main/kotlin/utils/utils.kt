package utils

import java.io.File

fun readInput(day: String): List<String> {
    return File("src/main/kotlin/day$day/input.txt")
        .readLines()
}

val directions = listOf(
    (-1 to 0),
    (1 to 0),
    (0 to -1),
    (0 to 1)
)
typealias Point = Pair<Int, Int>
