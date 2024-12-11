package day11

import utils.readInput

fun readInput() = readInput("11").single().split(" ").map { it.toLong() }

fun main() {
    val input = readInput()
    var counts = buildMap<Long, Long> {
        for (num in input) {
            merge(num, 1) { count, one -> count + one }
        }
    }
    repeat(25) {
        counts = buildMap {
            for ((num, count) in counts) {
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
    println(counts.values.sum())
}