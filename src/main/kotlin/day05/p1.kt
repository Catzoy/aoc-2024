package day05

import utils.readInput

fun Iterator<String>.readRules(): Map<Int, Set<Int>> {
    return buildMap {
        var line = next()
        do {
            val (x, y) = line.split("|").map { it.toInt() }
            put(x, getOrDefault(x, emptySet()) + y)
            line = next()
        } while (line.isNotEmpty())
    }
}

fun Iterator<String>.readUpdates(): List<List<Int>> {
    return buildList {
        while (hasNext()) {
            add(next().split(",").map { it.toInt() })
        }
    }
}

fun didBreakRules(rules: Map<Int, Set<Int>>, pages: List<Int>, j: Int): Boolean {
    val page = pages[j]
    val rule = rules.getOrDefault(page, emptySet())
    return pages.take(j).any { it in rule }
}

fun main() {
    val input = readInput("05").iterator()
    val (rules, updates) = input.readRules() to input.readUpdates()
    val result = updates.asSequence()
        .filter { pages ->
            pages.indices.none { j ->
                didBreakRules(rules, pages, j)
            }
        }.sumOf {
            it[it.size / 2]
        }
    print(result)
}