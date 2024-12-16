package day16

import utils.*
import kotlin.math.absoluteValue

typealias Move = Pair<Direction, Point>
typealias Path = List<Pair<Direction, Point>>

data class Successor(
    val move: Move,
    var parent: Successor? = null,
    var g: Int = 0,
    var h: Int = 0,
    var f: Int = 0,
)

fun watashiWaStar(
    matrix: List<String>,
    start: Move,
    end: Point,
): MutableList<Successor> {
    val closed = mutableListOf<Successor>()
    val open = mutableListOf<Successor>().apply {
        add(Successor(move = start, f = 0))
    }
    while (open.isNotEmpty()) {
        val q = open.minBy { s -> s.f }
        val successors = q.move.let { (direction, point) ->
            buildList {
                add(Successor(direction to point + direction))

                val adjacents = direction.adjacent.map { adjacent ->
                    Successor(adjacent to point + adjacent)
                }
                addAll(adjacents)
            }
        }

        for (successor in successors) {
            successor.parent = q

            val char = successor.move.second.let { (y, x) -> matrix[y][x] }
            if (char == '#') {
                continue
            }

            if (successor.move.second == end) {
                closed.apply { add(successor) }
                continue
            }

            successor.g = q.g + q.move.score(successor.move)
            successor.h = successor.move.second.manhattan(end)
            successor.f = successor.g + successor.h


            if (open.any { s -> s.move == successor.move && s.f < successor.f }) {
                continue
            }

            if (closed.any { s -> s.move == successor.move && s.f < successor.f }) {
                continue
            }

            open.add(successor)
        }

        closed.add(q)
        open.remove(q)
    }
    return closed
}

fun Move.score(other: Move): Int {
    return if (first == other.first) 1 else 1001
}

fun Point.manhattan(other: Point): Int {
    return (first - other.first).absoluteValue +
            (second - other.second).absoluteValue
}

fun calculateScore(path: Path): Int {
    return path.asSequence()
        .zipWithNext { a, b ->
            if (a.first == b.first) 1 else 1001
        }
        .sum()
}

fun main() {
    val input = readInput("16")
    val endPoint = input.indicesOf('E')
    val path = watashiWaStar(
        matrix = input,
        start = input.indicesOf('S').let { s -> (dRight to s) },
        end = endPoint,
    )
    val ends = path.filter { it.move.second == endPoint }
    val results = mutableMapOf<Int, MutableList<List<Move>>>()
    for (e in ends) {
        val solution = mutableListOf(e.move)
        var p = e.parent
        while (p != null) {
            solution.add(p.move)
            p = p.parent
        }
        val score = calculateScore(solution)
        results.getOrPut(score) { mutableListOf() }.add(solution)
    }

    results.minOf { it.key }.let { key ->
        val solutions = results.getValue(key)
        println("Best score: $key")
        val unique = solutions.asSequence().flatten().map { it.second }.toSet()
        println(unique.size)
    }
}