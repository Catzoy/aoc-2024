package day10

import utils.readInput

fun main() {
    val matrix = readInput("10").map { line -> line.map { c -> c.digitToInt() } }
    val positions = startingPositionsOf(matrix)
    val rating = positions.sumOf { point ->
        trailsOf(setOf(point), matrix).size
    }

    println(rating)
}