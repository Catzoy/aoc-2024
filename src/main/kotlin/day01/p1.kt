package day01

import java.io.File
import kotlin.math.absoluteValue

fun main() {
    val lines = File("src/main/kotlin/day01/input.txt")
        .readLines()

    val result = lines.asSequence()
        .flatMap { line ->
            line.split("   ")
                .map { it.toInt() }
                .withIndex()
        }
        .groupBy(
            keySelector = { it.index },
            valueTransform = { it.value }
        )
        .mapValues { (_, list) ->
            list.sorted()
        }
        .let { grouping ->
            grouping.getValue(0).zip(grouping.getValue(1))
        }
        .sumOf { (right, left) ->
            (right - left).absoluteValue
        }

    print(result)
}