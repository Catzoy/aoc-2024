package day05

import utils.readInput

fun Map<Int, Set<Int>>.didBreakRules(pages: List<Int>, j: Int): Boolean {
    val page = pages[j]
    val rule = getOrDefault(page, emptySet())
    return pages.take(j).any { it in rule }
}

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
    val failed = buildList {
        for (pages in updates) {
            var didFail = false
            for (j in pages.indices) {
                didFail = rules.didBreakRules(pages, j)
                if (didFail) break
            }
            if (didFail) {
                add(pages)
            }
        }
    }
    val fixed = buildList {
        for (pages in failed) {
            val p = pages.toMutableList()
            do {
                val i = p.indices.first { j ->
                    rules.didBreakRules(p, j)
                }
                val value = p.removeAt(i)
                p.add(i - 1, value)
            } while (p.indices.any { j -> rules.didBreakRules(p, j) })
            add(p)
        }
    }
    val result = fixed.sumOf { it[it.size / 2] }
    print(result)
}