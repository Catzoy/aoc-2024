package day03

import utils.readInput

fun main() {
    val mulRegex = Regex("""mul\((\d{1,3}),(\d{1,3})\)|(do\(\))|(don't\(\))""")
    val input = readInput(day = "03")
    val (result, _) = input.asSequence()
        .flatMap { line -> mulRegex.findAll(line) }
        .fold(0 to true) { acc, match ->
            val (accValue, accDo) = acc
            val matched = match.groupValues.drop(1).filter { it.isNotEmpty() }
            when {
                matched.first() == "do()" -> accValue to true

                matched.first() == "don't()" -> accValue to false

                accDo -> {
                    val mult = listOf(match.groupValues[1], match.groupValues[2])
                        .asSequence()
                        .map { elem -> elem.toInt() }
                        .reduce { x, elem -> x * elem }
                    return@fold (accValue + mult) to accDo
                }

                else -> accValue to accDo
            }
        }

    print(result)
}