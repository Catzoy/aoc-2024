package day01

import java.io.File

fun main() {
    val lines = File("src/main/kotlin/day01/input.txt")
        .readLines()

    val (lefts, rights) = lines.asSequence()
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
            grouping.getValue(0) to grouping.getValue(1)
        }

    val occurrences = buildMap<Int, Int> {
        rights.forEach {
            put(it, getOrDefault(it, 0) + 1)
        }
    }
    val result = lefts.sumOf {
        it * occurrences.getOrDefault(it, 0)
    }
    print(result)
}