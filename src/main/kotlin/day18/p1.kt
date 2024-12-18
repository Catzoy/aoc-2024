package day18

import utils.*

fun readInput(): List<Point> {
    val point = Regex("""(\d+),(\d+)""")
    return readInput("18").map { line ->
        val (x, y) = point.find(line)!!.destructured
        Pair(y.toInt(), x.toInt())
    }
}

data class Successor(
    val point: Point,
    val parent: Successor? = null,
    var g: Int = 0,
    var h: Int = 0,
    var f: Int = 0,
)

fun aStarFinding(
    matrix: List<List<Char>>,
    start: Point,
    end: Point,
): MutableList<Successor> {
    val closed = mutableListOf<Successor>()
    val open = mutableListOf<Successor>().apply {
        add(Successor(point = start, f = 0))
    }
    while (open.isNotEmpty()) {
        val q = open.minBy { s -> s.f }.also {
            open.remove(it)
        }
        val successors = directions.map { direction ->
            Successor(
                point = q.point + direction,
                parent = q,
            )
        }

        for (successor in successors) {
            val char = successor.point.let { (y, x) ->
                matrix.getOrNull(y)?.getOrNull(x)
            } ?: continue
            if (char == '#') {
                continue
            }

            if (successor.point == end) {
                return closed.apply { add(successor) }
            }

            successor.g = q.g + q.point.manhattan(successor.point)
            successor.h = successor.point.manhattan(end)
            successor.f = successor.g + successor.h


            if (open.any { s -> s.point == successor.point && s.f <= successor.f }) {
                continue
            }

            if (closed.any { s -> s.point == successor.point && s.f <= successor.f }) {
                continue
            }

            open.add(successor)
        }

        closed.add(q)
    }
    return closed
}

fun main() {
    val grid = MutableList(71) { MutableList(71) { '.' } }
    val points = readInput()
    points.take(1024).forEach { (y, x) ->
        grid[y][x] = '#'
    }
    grid.forEach { row ->
        println(row.joinToString(""))
    }
    val representation = aStarFinding(
        matrix = grid,
        start = (0 to 0),
        end = (70 to 70),
    )
    var current: Successor? = representation.last()
    var counter = 1
    while (current != null) {
        val (y, x) = current.point
        grid[y][x] = 'O'
        current = current.parent
        counter++
    }
    println()
    grid.forEach { row ->
        println(row.joinToString(""))
    }
    println(counter)
}