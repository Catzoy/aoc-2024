package day10

import utils.Point
import utils.directions
import utils.readInput

typealias Trail = List<Point>

fun startingPositionsOf(matrix: List<List<Int>>): Set<Point> {
    return matrix.withIndex().flatMap { (y, line) ->
        line.withIndex()
            .asSequence()
            .filter { (_, p) -> p == 0 }
            .map { (x, _) -> y to x }
    }.toSet()
}

fun trailsOf(path: Set<Point>, matrix: List<List<Int>>): List<Trail> {
    val (y, x) = path.last()
    val ref = matrix[y][x]
    if (ref == 9) {
        return listOf(path.toList())
    }

    return buildList {
        val target = ref + 1
        for ((dy, dx) in directions) {
            if (matrix.getOrNull(y + dy)?.getOrNull(x + dx) == target) {
                val newPath = path + (y + dy to x + dx)
                addAll(trailsOf(newPath, matrix))
            }
        }
    }
}

fun main() {
    val matrix = readInput("10").map { line -> line.map { c -> c.digitToInt() } }
    val positions = startingPositionsOf(matrix)
    val score = positions.sumOf { point ->
        trailsOf(setOf(point), matrix)
            .map { it.last() }
            .toSet()
            .count()
    }

    println(score)

}