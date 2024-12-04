package day04

import utils.readInput

fun main() {
    val input = readInput("04")
    val lines = input.map { it.toCharArray() }
    val result = lines.withIndex()
        .sumOf { (i, line) ->
            line.withIndex()
                .filter { (_, c) -> c == 'A' }
                .count { (j, c) ->
                    val topLeft = lines.getOrNull(i - 1)?.getOrNull(j - 1)
                        ?: return@count false
                    val topRight = lines.getOrNull(i - 1)?.getOrNull(j + 1)
                        ?: return@count false
                    val bottomLeft = lines.getOrNull(i + 1)?.getOrNull(j - 1)
                        ?: return@count false
                    val bottomRight = lines.getOrNull(i + 1)?.getOrNull(j + 1)
                        ?: return@count false
                    val masses = sequence {
                        yield("$topLeft$c$bottomRight")
                        yield("$topRight$c$bottomLeft")
                        yield("$bottomLeft$c$topRight")
                        yield("$bottomRight$c$topLeft")
                    }
                    masses.count { it == "MAS" } == 2
                }
        }
    print(result)
}