package day20

import utils.*
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

fun findCheats(
    points: Map<Point, Int>,
    matrix: List<List<Char>>,
    edgeMatrix: Map<Int, List<Point>>,
) = buildMap<Int, Set<Cut>> {
    val total = points.size
    var counter = 0
    for ((point, distance) in points) {
        println("$counter / $total")
        counter++

        for ((_, edges) in edgeMatrix) {
            val breaks = edges.map { dir -> (point + dir) }
            for (next in breaks) {
                val nC = matrix.getOrNull(next.first)?.getOrNull(next.second)
                if (nC == null || nC == '#') continue

                val diff = next - point
                val cutLen = diff.first.absoluteValue + diff.second.absoluteValue
                val cut = (points.getValue(next) - distance).absoluteValue - cutLen
                if (cut > 0) {
                    put(cut, getOrDefault(cut, emptySet()) + keyOf(point, next))
                }
            }
        }
    }
}

fun edgesOf(point: Point, n: Int) = buildMap {
    var picks = directions.map { dir -> point + dir }

    for (i in 1 until n) {
        picks = directions.withIndex().map { (j, dir) -> picks[j] + dir }
        val combos = listOf(
            picks[0] to picks[1],
            picks[1] to picks[2],
            picks[2] to picks[3],
            picks[3] to picks[0],
        )
        val edges = buildList {
            for ((p1, p2) in combos) {
                val step = (p2 - p1).let { (y, x) -> (y / i) to (x / i) }
                var current = p1
                while (current != p2) {
                    current += step
                    add(current)
                }
                add(p2)
            }
        }
        put(i, edges)
    }
}

fun main() {
    val (s, e, matrix) = readInput()
    val visited = walk(s, e, matrix)
    val edgeMatrix = edgesOf(point = 0 to 0, n = 2)
    val cheats = findCheats(visited, matrix, edgeMatrix)
    val applicable = cheats
        .filter { it.key > 100 }
        .values
        .sumOf { it.size }
    println(applicable)
}