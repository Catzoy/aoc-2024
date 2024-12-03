package day03

import utils.readInput

fun main() {
    val mulRegex = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")
    val input = readInput(day = "03")
    val result = input.asSequence()
        .map { line -> mulRegex.find(line) }
        .filterNotNull()
        .map { match ->
            listOf(match.groupValues[1], match.groupValues[2])
                .asSequence()
                .map { elem -> elem.toInt() }
                .reduce { acc, elem -> acc * elem }
        }
        .sum()
    print(result)
}