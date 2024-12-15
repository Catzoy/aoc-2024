package day15

import utils.Point
import utils.readInput

private fun readInput(): Pair<List<String>, String> {
    val input = readInput("15")
    val iterator = input.iterator()
    val warehouse = buildList {
        var line = iterator.next()
        do {
            add(line)
            line = iterator.next()
        } while (line.isNotEmpty())
    }
    val moves = iterator.asSequence().joinToString("")
    return warehouse to moves
}

fun findRobot(warehouse: List<String>): Point? {
    for (y in warehouse.indices) {
        for (x in warehouse[y].indices) {
            if (warehouse[y][x] == '@') {
                return y to x
            }
        }
    }
    return null
}

fun MutableList<MutableList<Char>>.execute(robot: Point, move: Char): Point {
    var (rY, rX) = robot
    when (move) {
        '<' -> {
            var left = rX - 1
            while (this[rY][left] == 'O') {
                left--
            }
            if (this[rY][left] == '.') {
                for (l in left..<rX - 1) {
                    this[rY][l] = 'O'
                }
                this[rY][rX - 1] = '@'
                this[rY][rX] = '.'
                rX--
            }
        }

        '>' -> {
            var right = rX + 1
            while (this[rY][right] == 'O') {
                right++
            }
            if (this[rY][right] == '.') {
                for (r in (rX + 2)..right) {
                    this[rY][r] = 'O'
                }
                this[rY][rX + 1] = '@'
                this[rY][rX] = '.'
                rX++
            }
        }

        '^' -> {
            var up = rY - 1
            while (this[up][rX] == 'O') {
                up--
            }
            if (this[up][rX] == '.') {
                for (u in up..<rY - 1) {
                    this[u][rX] = 'O'
                }
                this[rY - 1][rX] = '@'
                this[rY][rX] = '.'
                rY--
            }
        }

        'v' -> {
            var down = rY + 1
            while (this[down][rX] == 'O') {
                down++
            }
            if (this[down][rX] == '.') {
                for (d in rY + 2..down) {
                    this[d][rX] = 'O'
                }
                this[rY + 1][rX] = '@'
                this[rY][rX] = '.'
                rY++
            }
        }
    }
    return rY to rX
}

fun simulate(warehouse: List<String>, moves: String): List<List<Char>> {
    return warehouse.map { it.toMutableList() }.toMutableList().apply {
        var robot = findRobot(warehouse) ?: error("Robot not found")
        for (move in moves) {
            robot = execute(robot, move)
        }
    }
}

fun calculateGPS(organizedW: List<List<Char>>): Int {
    return organizedW.withIndex().fold(0) { acc1, (y, row) ->
        acc1 + row.withIndex().fold(0) { acc2, (x, char) ->
            acc2 + if (char == 'O') (x + y * 100) else 0
        }
    }
}

fun main() {
    val (warehouse, moves) = readInput()
    val organizedW = simulate(warehouse, moves)
    val result = calculateGPS(organizedW)
    println(result)
}