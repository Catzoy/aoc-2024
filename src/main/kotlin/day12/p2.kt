package day12

import utils.*

fun sidesOf(region: Set<Point>): List<List<Point>> {
    val outers = region.associateWith { point ->
        directions.map { direction -> (point + direction to direction) }
            .filter { (out, _) -> !region.contains(out) }
    }
    val sides = mutableListOf<Pair<Direction, List<Point>>>()
    for (point in region.sortedBy { it.first }) {
        val outs = outers[point] ?: continue
        for ((out, dir) in outs) {
            val adj = dir.adjacent
            val existing = sides.indexOfFirst { (d, l) ->
                d == dir && listOf(l.first(), l.last()).any { p -> adj.any { d2 -> p + d2 == out } }
            }
            if (existing > -1) {
                val (d, l) = sides[existing]
                sides[existing] = d to (l + out).sortedWith(compareBy({ it.first }, { it.second }))
            } else {
                sides.add(dir to listOf(out))
            }
        }
    }
    return sides.map { it.second }
}

fun main() {
    val input = readInput("12")
    val garden = regionsOf(input)
    val result = totalPriceOf(garden) { region ->
        sidesOf(region).size * areaOf(region)
    }
    println(result)
}