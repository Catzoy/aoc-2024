package utils

import java.io.File

fun readInput(day: String): List<String> {
    return File("src/main/kotlin/day$day/input.txt")
        .readLines()
}