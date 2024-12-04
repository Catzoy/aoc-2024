package day04

import utils.readInput

fun main() {
    val input = readInput("04")
    val lines = input.map { it.toCharArray() }
    var wordCount = 0
    for ((i, line) in lines.withIndex()) {
        for ((j, c) in line.withIndex()) {
            if (c != 'A') continue
            val topLeft = lines.getOrNull(i - 1)?.getOrNull(j - 1)
            val topRight = lines.getOrNull(i - 1)?.getOrNull(j + 1)
            val bottomLeft = lines.getOrNull(i + 1)?.getOrNull(j - 1)
            val bottomRight = lines.getOrNull(i + 1)?.getOrNull(j + 1)
            val masses = sequence {
                yield("$topLeft$c$bottomRight")
                yield("$topRight$c$bottomLeft")
                yield("$bottomLeft$c$topRight")
                yield("$bottomRight$c$topLeft")
            }
            wordCount += if (masses.count { it == "MAS" } == 2) 1 else 0
        }
    }
    print(wordCount)
}