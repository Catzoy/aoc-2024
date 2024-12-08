package day08

import utils.readInput

fun inBounds(point: Pair<Int, Int>, lastPoint: Pair<Int, Int>): Boolean {
    return point.first in 0 until lastPoint.first
            && point.second in 0 until lastPoint.second
}

fun main() {
    val input = readInput("08")
    val antennas = buildMap<Char, Set<Pair<Int, Int>>> {
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
                var fromA = (a.first + diff.first to a.second + diff.second)
                while (inBounds(fromA, lastPoint)) {
                    add(fromA)
                    fromA = (fromA.first + diff.first to fromA.second + diff.second)
                }
                var fromB = (b.first - diff.first to b.second - diff.second)
                while (inBounds(fromB, lastPoint)) {
                    add(fromB)
                    fromB = (fromB.first - diff.first to fromB.second - diff.second)
                }
            }
            if (locations.size > 1) {
                addAll(locations)
            }
        }
    }
    val result = antinodes.size
    println(result)
}