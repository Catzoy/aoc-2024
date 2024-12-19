package day19

import utils.readInput

fun readInput(): Pair<Set<String>, List<String>> {
    val input = readInput("19").iterator()
    val available = input.next().split(", ").sortedByDescending { it.length }.toSet()
    input.next() // drop blank line
    val designs = input.asSequence().toList()
    return Pair(available, designs)
}

val cache = mutableMapOf<String, Boolean>()

fun findCombinations(
    design: String,
    available: Set<String>,
    combination: List<String>,
): Boolean {
    if (design.isEmpty()) {
        return true
    }

    val openings = available.filter { design.startsWith(it) }
    if (openings.isEmpty()) {
        return false
    }

    return openings.any { opening ->
        val key = design.drop(opening.length)
        cache.getOrPut(key) {
            findCombinations(key, available, combination + opening)
        }
    }
}

fun main() {
    val (available, designs) = readInput()
    var counter = 0
    for (design in designs) {
        val hasCombinations = findCombinations(design, available, emptyList())
        print("DESIGN $design -> ")
        if (hasCombinations) {
            println("YES")
            counter++
        } else {
            println("NO")
        }
    }
    println(counter)
}
