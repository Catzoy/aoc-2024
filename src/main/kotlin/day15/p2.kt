package day15

import utils.Point

fun scale(warehouse: List<String>): List<String> {
    return warehouse.map { line ->
        line.toCharArray().joinToString(separator = "") { c ->
            when (c) {
                '@' -> "@."
                'O' -> "[]"
                else -> "$c$c"
            }
        }
    }
}

fun MutableList<MutableList<Char>>.executeScaled(robot: Point, move: Char): Point {
    var (rY, rX) = robot
    when (move) {
        '<' -> {
            var left = rX - 1
            while (this[rY][left] == ']') {
                left -= 2
            }
            if (this[rY][left] == '.') {
                for (l in left..rX - 3 step 2) {
                    this[rY][l] = '['
                    this[rY][l + 1] = ']'
                    this[rY][l + 2] = '.'
                }
                this[rY][rX - 1] = '@'
                this[rY][rX] = '.'
                rX--
            }
        }

        '>' -> {
            var right = rX + 1
            while (this[rY][right] == '[') {
                right += 2
            }
            if (this[rY][right] == '.') {
                for (r in right downTo rX + 3 step 2) {
                    this[rY][r] = ']'
                    this[rY][r - 1] = '['
                    this[rY][r - 2] = '.'
                }
                this[rY][rX + 1] = '@'
                this[rY][rX] = '.'
                rX++
            }
        }

        '^' -> {
            val box = setOf('[', ']')
            var up = rY - 1
            var rxs = setOf(rX).toSortedSet()
            val meets = mutableMapOf<Int, Set<Int>>()
            while (rxs.any { this[up][it] in box } && rxs.none { this[up][it] == '#' }) {
                val boxRxs = rxs.flatMap { r ->
                    when (this[up][r]) {
                        '[' -> listOf(r, r + 1)
                        ']' -> listOf(r - 1, r)
                        else -> emptyList()
                    }
                }.toSortedSet()
                meets[up] = boxRxs
                rxs = boxRxs

                up--
            }
            if (rxs.all { this[up][it] == '.' }) {
                for ((y, xs) in meets.toSortedMap()) {
                    for ((xL, xR) in xs.chunked(2)) {
                        this[y][xL] = '.'
                        this[y][xR] = '.'
                        this[y - 1][xL] = '['
                        this[y - 1][xR] = ']'
                    }
                }
                this[rY - 1][rX] = '@'
                this[rY][rX] = '.'
                rY--
            }
        }

        'v' -> {
            val box = setOf('[', ']')
            var down = rY + 1
            var rxs = setOf(rX).toSortedSet()
            val meets = mutableMapOf<Int, Set<Int>>()
            while (rxs.any { this[down][it] in box } && rxs.none { this[down][it] == '#' }) {
                val boxRxs = rxs.flatMap { r ->
                    when (this[down][r]) {
                        '[' -> listOf(r, r + 1)
                        ']' -> listOf(r - 1, r)
                        else -> emptyList()
                    }
                }.toSortedSet()
                meets[down] = boxRxs
                rxs = boxRxs

                down++
            }
            if (rxs.all { this[down][it] == '.' }) {
                for ((y, xs) in meets.toSortedMap(compareByDescending { it })) {
                    for ((xL, xR) in xs.chunked(2)) {
                        this[y][xL] = '.'
                        this[y][xR] = '.'
                        this[y + 1][xL] = '['
                        this[y + 1][xR] = ']'
                    }
                }
                this[rY + 1][rX] = '@'
                this[rY][rX] = '.'
                rY++
            }
        }
    }
    return rY to rX
}


fun simulateScaled(warehouse: List<String>, moves: String): List<List<Char>> {
    return warehouse.map { it.toMutableList() }.toMutableList().apply {
        var robot = findRobot(warehouse) ?: error("Robot not found")
        for (move in moves) {
            robot = executeScaled(robot, move)
        }
    }
}

fun calculateScaledGPS(organizedW: List<List<Char>>): Int {
    val box = setOf('[', ']')
    return organizedW.withIndex().fold(0) { acc1, (y, row) ->
        val rowValue = y * 100
        acc1 + row.indices.asSequence()
            .filter { i -> row[i] in box }
            .chunked(2)
            .fold(0) { acc2, (xL) ->
                acc2 + xL + rowValue
            }
    }
}

fun main() {
    val (warehouse, moves) = readInput()
    val scaled = scale(warehouse)
    val organizedW = simulateScaled(scaled, moves)
    val result = calculateScaledGPS(organizedW)
    println(result)
}