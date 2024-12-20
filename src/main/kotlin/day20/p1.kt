package day20

import utils.Point
import utils.directions
import utils.plus
import utils.readInput
import kotlin.math.absoluteValue

fun readInput(): Triple<Point, Point, List<List<Char>>> {
    var s: Point? = null
    var e: Point? = null
    val input = readInput("20").map { it.toList() }
    for ((y, row) in input.withIndex()) {
        for ((x, char) in row.withIndex()) {
            when (char) {
                'S' -> s = Point(y, x)
                'E' -> e = Point(y, x)
            }
        }
    }
    return Triple(
        s ?: error("No start point found"),
        e ?: error("No end point found"),
        input
    )
}

fun walk(s: Point, e: Point, matrix: List<List<Char>>) = buildMap<Point, Int> {
    var current = s
    put(current, 0)

    while (current != e) {
        val next = directions.asSequence()
            .map { dir -> dir + current }
            .filter { (y, x) -> matrix[y][x] != '#' }
            .filter { p -> !contains(p) }
            .single()
        put(next, getOrDefault(current, 0) + 1)
        current = next
    }
}

fun keyOf(point: Point, next: Point): Pair<Point, Point> {
    if (next.first < point.first) {
        return next to point
    }
    if (next.first == point.first) {
        return if (next.second < point.second) {
            next to point
        } else if (next.second > point.second) {
            point to next
        } else {
            error("Invalid pair $next to $point")
        }
    }
    return point to next
}
typealias Cut = Pair<Point, Point>

fun findCheats(points: Map<Point, Int>, matrix: List<List<Char>>) = buildMap<Int, Set<Cut>> {
    for ((point, distance) in points) {
        val breaks = directions.map { dir -> (point + dir) to (point + dir + dir) }
        for ((skip, next) in breaks) {
            if (matrix.getOrNull(skip.first)?.getOrNull(skip.second) != '#') continue
            if (matrix.getOrNull(next.first)?.getOrNull(next.second) != '.') continue

            val cut = (points.getValue(next) - distance).absoluteValue - 2
            put(cut, getOrDefault(cut, emptySet()) + keyOf(point, next))
        }
    }
}

fun main() {
    val (s, e, matrix) = readInput()
    val visited = walk(s, e, matrix)
    val cheats = findCheats(visited, matrix)
    val applicable = cheats
        .filter { it.key > 99 }
        .values
        .sumOf { it.size }
    println(applicable)
}