package day11

import utils.readInput

fun readInput(): Map<Long, Long> {
    val input = readInput("11").single().split(" ").map { it.toLong() }
    return buildMap {
        for (num in input) {
            merge(num, 1) { count, one -> count + one }
        }
    }
}

fun iterate(previous: Map<Long, Long>): Map<Long, Long> {
    return buildMap {
        for ((num, count) in previous) {
            if (num == 0L) {
                merge(1, count) { c1, c2 -> c1 + c2 }
                continue
            }

            val str = num.toString()
            if (str.length % 2 == 0) {
                val right = str.take(str.length / 2)
                merge(right.toLong(), count) { c1, c2 -> c1 + c2 }

                val left = str.takeLast(str.length / 2)
                merge(left.toLong(), count) { c1, c2 -> c1 + c2 }
                continue
            }

            val next = num * 2024
            merge(next, count) { c1, c2 -> c1 + c2 }
        }
    }
}

fun main() {
    var counts = readInput()
    repeat(25) {
        counts = iterate(counts)
    }
    println(counts.values.sum())
}