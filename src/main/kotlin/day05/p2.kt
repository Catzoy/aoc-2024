package day05

import utils.readInput

fun List<Int>.indexOfRuleBreak(rules: Map<Int, Set<Int>>): Int? {
    return indices.firstOrNull { j -> didBreakRules(rules, this, j) }
}

fun main() {
    val input = readInput("05").iterator()
    val (rules, updates) = input.readRules() to input.readUpdates()
    val result = updates.asSequence()
        .filter { pages ->
            pages.indices.any { j ->
                didBreakRules(rules, pages, j)
            }
        }
        .map { pages ->
            pages.toMutableList().apply {
                var i = indexOfRuleBreak(rules)
                while (i != null) {
                    val value = removeAt(i)
                    add(i - 1, value)
                    i = indexOfRuleBreak(rules)
                }
            }
        }
        .sumOf {
            it[it.size / 2]
        }
    print(result)
}