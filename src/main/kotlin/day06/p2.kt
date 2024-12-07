package day06

import utils.readInput

fun main() {
    val input = readInput("06")
    val reference = input.toWalkable().walk()!!
    val positions = reference.flatMapIndexed { y, chars ->
        chars.flatMapIndexed { x, c ->
            if (c == 'X') listOf(y to x) else emptyList()
        }
    }
    val result = positions.asSequence()
        .distinct()
        .mapNotNull { override ->
            runCatching { input.toWalkable().walk(override) }
                .map { schema -> if (schema == null) override else null }
                .getOrNull()
        }
        .count()
    print(result)
}