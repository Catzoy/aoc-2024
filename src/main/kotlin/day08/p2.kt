package day08

import utils.readInput

fun drawLine(a: Point, b: Point, lastPoint: Point): Set<Point> {
    val diff = (a.first - b.first to a.second - b.second)
    return buildSet {
        var next = (a.first + diff.first to a.second + diff.second)
        while (inBounds(next, lastPoint)) {
            add(next)
            next = (next.first + diff.first to next.second + diff.second)
        }
    }
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
                addAll(drawLine(a, b, lastPoint))
                addAll(drawLine(b, a, lastPoint))
            }
            if (locations.size > 1) {
                addAll(locations)
            }
        }
    }
    val result = antinodes.size
    println(result)
}