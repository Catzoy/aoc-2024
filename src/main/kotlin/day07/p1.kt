package day07

import utils.readInput

fun String.toSetup(): Pair<Long, List<Long>> {
    val (test, values) = split(":")
    val num = values.drop(1).split(" ")
    return (test.toLong() to num.map { it.toLong() })
}

val base = listOf(
    listOf("+"),
    listOf("*"),
)

fun List<String>.mixUp(size: Int): List<List<String>> {
    if (size == 2) {
        return base
    }
    val next = mixUp(size - 1)
    return base.flatMap { part ->
        next.map { x -> part + x }
    }
}

fun main() {
    val operators = listOf("+", "*")
    val result = readInput("07").asSequence()
        .map { line -> line.toSetup() }
        .mapNotNull { (test, nums) ->
            val permutations = operators.mixUp(nums.size)
            val perm = permutations.find { perm ->
                nums.reduceIndexed { index, acc, elem ->
                    when (val operator = perm[index - 1]) {
                        "+" -> acc + elem
                        "*" -> acc * elem
                        else -> throw IllegalArgumentException("Unknown operator $operator")
                    }
                } == test
            }
            if (perm == null) null else test
        }
        .sum()
    println(result)
}