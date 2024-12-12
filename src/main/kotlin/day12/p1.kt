package day12

import utils.Point
import utils.directions
import utils.readInput

fun regionOf(point: Point, input: List<String>): Set<Point> {
    val checkers = mutableSetOf(point)
    val region = mutableSetOf(point)
    while (checkers.isNotEmpty()) {
        val (y, x) = checkers.first().also {
            checkers.remove(it)
        }
        for ((dy, dx) in directions) {
            val neighbor = (y + dy) to (x + dx)
            if (neighbor in region) continue

            val (nY, nX) = neighbor
            if (input.getOrNull(nY)?.getOrNull(nX) == input[y][x]) {
                checkers.add(neighbor)
                region.add(neighbor)
            }
        }
    }
    return region
}

fun regionsOf(input: List<String>): Map<Char, List<Set<Point>>> {
    return buildMap<Char, MutableList<Set<Point>>> {
        for ((y, line) in input.withIndex()) {
            for ((x, c) in line.withIndex()) {
                val known = getOrPut(c) { mutableListOf() }
                if (known.none { r -> r.contains(y to x) }) {
                    val region = regionOf(y to x, input)
                    known.add(region)
                }
            }
        }
    }
}

fun areaOf(region: Set<Point>): Long {
    return region.size.toLong()
}

fun perimeterOf(region: Set<Point>): Int {
    return region.sumOf { (y, x) ->
        4 - directions.count { (dy, dx) ->
            region.contains(y + dy to x + dx)
        }
    }
}

fun totalPriceOf(garden: Map<Char, List<Set<Point>>>): Long {
    return garden.values.sumOf { regions ->
        regions.sumOf { region -> perimeterOf(region) * areaOf(region) }
    }
}

fun main() {
    val input = readInput("12")
    val garden = regionsOf(input)
    val result = totalPriceOf(garden)
    println(result)
}