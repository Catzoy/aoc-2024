package day05

import utils.readInput

fun main() {
    val input = readInput("05").iterator()
    var didReadAllRules = false
    val rules = buildMap<Int, Set<Int>> {
        while (!didReadAllRules) {
            val line = input.next()
            if (line.isEmpty()) {
                didReadAllRules = true
                continue
            }
            val (x, y) = line.split("|").map { it.toInt() }
            put(x, getOrDefault(x, emptySet()) + y)
        }
    }
    val updates = buildList {
        while (input.hasNext()) {
            add(input.next().split(",").map { it.toInt() })
        }
    }
    var accumulator = 0
    for (pages in updates) {
        var didFail = false
        for ((j, page) in pages.withIndex()) {
            val rule = rules[page] ?: continue
            val preceding = pages.subList(0, j)
            didFail = preceding.any { it in rule }
            if (didFail) break
        }
        if (!didFail) {
            accumulator += pages[pages.size / 2]
        }
    }
    print(accumulator)
}