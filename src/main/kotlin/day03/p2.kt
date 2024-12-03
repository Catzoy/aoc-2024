package day03

import utils.readInput

sealed interface Mult {
    val value: Int

    data class Enabled(override val value: Int) : Mult
    data class Disabled(override val value: Int) : Mult
}

fun main() {
    val mulRegex = Regex("""mul\((\d{1,3}),(\d{1,3})\)|(do\(\))|(don't\(\))""")
    val input = readInput(day = "03")
    val result = input.findMatches(mulRegex)
        .fold<List<String>, Mult>(Mult.Enabled(0)) { acc, matches ->
            when {
                matches.first() == "do()" -> Mult.Enabled(acc.value)

                matches.first() == "don't()" -> Mult.Disabled(acc.value)

                acc is Mult.Enabled -> acc.copy(acc.value + matches.evaluateMult())

                else -> acc
            }
        }

    print(result.value)
}