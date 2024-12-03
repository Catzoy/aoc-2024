package day03

import utils.readInput

fun List<String>.evaluateMult() = asSequence()
    .map { elem -> elem.toInt() }
    .reduce { acc, elem -> acc * elem }

fun List<String>.findMatches(multRegex: Regex) = asSequence()
    .flatMap { line -> multRegex.findAll(line) }
    .map { match -> match.groupValues.drop(1).filter { it.isNotEmpty() } }

fun main() {
    val mulRegex = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")
    val input = readInput(day = "03")
    val result = input.findMatches(mulRegex)
        .map { matches -> matches.evaluateMult() }
        .sum()
    print(result)
}