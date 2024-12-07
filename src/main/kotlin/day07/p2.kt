package day07

import utils.readInput

fun main() {
    val operators = listOf("+", "*", "||")
    val result = readInput("07").asSequence()
        .map { line -> line.toSetup() }
        .retainValid(operators)
        .sum()
    println(result)
}