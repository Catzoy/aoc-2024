package day07

import utils.readInput

fun String.toSetup(): Pair<Long, List<Long>> {
    val (test, values) = split(":")
    val num = values.drop(1).split(" ")
    return (test.toLong() to num.map { it.toLong() })
}

fun List<String>.mixUp(size: Int, base: List<List<String>>): List<List<String>> {
    if (size == 2) {
        return base
    }
    val next = mixUp(size - 1, base)
    return base.flatMap { part ->
        next.map { x -> part + x }
    }
}

fun List<List<String>>.findCombination(test: Long, nums: List<Long>): List<String>? = find { perm ->
    val res = nums.reduceIndexed { index, acc, elem ->
        when (val operator = perm[index - 1]) {
            "+" -> acc + elem
            "*" -> acc * elem
            "||" -> "$acc$elem".toLong()
            else -> throw IllegalArgumentException("Unknown operator $operator")
        }
    }
    res == test
}

fun Sequence<Pair<Long, List<Long>>>.retainValid(operators: List<String>): Sequence<Long> {
    val base = operators.map { listOf(it) }
    return mapNotNull { (test, nums) ->
        val permutations = operators.mixUp(nums.size, base)
        val perm = permutations.findCombination(test, nums)
        if (perm == null) null else test
    }
}

fun main() {
    val operators = listOf("+", "*")
    val result = readInput("07").asSequence()
        .map { line -> line.toSetup() }
        .retainValid(operators)
        .sum()
    println(result)
}