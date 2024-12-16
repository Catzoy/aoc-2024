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
): MutableList<Successor>? {
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
                return closed.apply { add(successor) }
            }

            successor.g = q.g + q.move.score(successor.move)
            successor.h = successor.move.second.manhattan(end)
            successor.f = successor.g + successor.h

            if (open.any { s -> s.move.second == successor.move.second && s.f < successor.f }) {
                continue
            }
            if (closed.any { s -> s.move.second == successor.move.second && s.f < successor.f }) {
                continue
            }

            open.add(successor)
        }

        closed.add(q)
        open.remove(q)
    }
    return null
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
    val s = input.indicesOf('S')
    val e = input.indicesOf('E')
    val position = (dRight to s)
    val path = watashiWaStar(
        matrix = input,
        start = position,
        end = e
    ) ?: error("Unsolvable maze!")

    val m = input.map { it.toMutableList() }.toMutableList()
    var p = path.last()
    val solution = mutableListOf<Move>()
    while (p.parent != null) {
        solution.add(p.move)
        p.move.second.let { (y, x) -> m[y][x] = 'X' }
        p = p.parent!!
    }
    solution.add(p.move)
    for (row in m) {
        println(row.joinToString(""))
    }

    println("Score: ${calculateScore(solution.reversed())}")
}