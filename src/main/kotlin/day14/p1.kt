package day14

import utils.Point
import utils.readInput

typealias Robots = List<Pair<Point, Point>>
typealias Room = List<List<List<Int>>>

fun readInput(): List<Pair<Point, Point>> {
    val robot = Regex("p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)")
    return readInput("14").map { line ->
        val (pX, pY, vX, vY) = robot.find(line)!!.groupValues.drop(1).map { it.toInt() }
        (pY to pX) to (vY to vX)
    }
}

fun simulate(dimensions: Pair<Int, Int>, seconds: Int, robots: Robots): Room {
    val (wide, tall) = dimensions
    val room = List(tall) { MutableList(wide) { emptyList<Int>() } }
    robots.forEachIndexed { index, (p, v) ->
        val (pY, pX) = p
        val (vY, vX) = v
        val rY = ((pY + vY * seconds) % tall).let { if (it < 0) it + tall else it }
        val rX = ((pX + vX * seconds) % wide).let { if (it < 0) it + wide else it }
        room[rY][rX] = buildList {
            addAll(room[rY][rX])
            add(index)
        }
    }
    return room
}

fun Room.countIn(quadrant: Pair<IntRange, IntRange>): Int {
    val (yRange, xRange) = quadrant
    return yRange.sumOf { y ->
        xRange.sumOf { x ->
            this[y][x].size
        }
    }
}

val Room.safetyFactor: Int
    get() {
        val (tall, wide) = size to first().size
        val (qY, qX) = tall / 2 to wide / 2
        return listOf(
            (0 until qY) to (0 until qX),
            (qY + 1 until tall) to (0 until qX),
            (0 until qY) to (qX + 1 until wide),
            (qY + 1 until tall) to (qX + 1 until wide),
        ).map { quadrant ->
            countIn(quadrant)
        }.reduce { acc, i ->
            acc * i
        }
    }

fun main() {
    val (wide, tall) = 11 to 7
    val robots = readInput()
    val room = simulate(
        dimensions = (wide to tall),
        seconds = 100,
        robots = robots,
    )
    val result = room.safetyFactor
    println(result)
}