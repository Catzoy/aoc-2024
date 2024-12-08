package day08

import utils.readInput

typealias Point = Pair<Int, Int>

fun <T> combinationsOf(locations: Set<T>, size: Int): List<List<T>> {
    if (size == 1) {
        return locations.map { listOf(it) }
    }
    return locations.flatMap { location ->
        val next = combinationsOf(locations - location, size - 1)
        next.map { x -> x + location }
    }
}

fun inBounds(point: Point, lastPoint: Point): Boolean {
    return point.first in 0 until lastPoint.first
            && point.second in 0 until lastPoint.second
}

fun main() {
    val input = readInput("08")
    val antennas = buildMap<Char, Set<Point>> {
        for ((y, line) in input.withIndex()) {
            for ((x, c) in line.withIndex()) {
                if (c != '.') {
                    val entry = getOrPut(c) { emptySet() }
                    put(c, entry + (y to x))
                }
            }
        }
    }
    val lastPoint = (input.size to input.first().length)
    val antinodes = buildSet {
        for (locations in antennas.values) {
            val combinations = combinationsOf(locations, 2)
            for ((a, b) in combinations) {
                val diff = (a.first - b.first to a.second - b.second)
                val nodes = listOf(
                    (a.first + diff.first to a.second + diff.second),
                    (b.first - diff.first to b.second - diff.second)
                ).filter { point -> inBounds(point, lastPoint) }
                addAll(nodes)
            }
        }
    }
    val result = antinodes.size
    println(result)
}