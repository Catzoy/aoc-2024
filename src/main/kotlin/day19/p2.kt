package day19

val cache2 = mutableMapOf<String, ULong>()

fun findCombinations2(
    design: String,
    available: Set<String>,
    combination: List<String>,
): ULong {
    if (design.isEmpty()) {
        return 1uL
    }

    val openings = available.filter { design.startsWith(it) }
    if (openings.isEmpty()) {
        return 0uL
    }

    return openings.sumOf { opening ->
        val key = design.drop(opening.length)
        cache2.getOrPut(key) {
            findCombinations2(key, available, combination + opening)
        }
    }
}

fun main() {
    val (available, designs) = readInput()
    var counter = 0uL
    for (design in designs) {
        val combos = findCombinations2(design, available, emptyList())
        println("DESIGN $design -> $combos")
        counter += combos
    }
    println(counter)
}
