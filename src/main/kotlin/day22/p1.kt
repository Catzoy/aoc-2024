package day22

import utils.readInput

const val m = 16777216u

fun iterate(number: ULong): ULong {
    var number = number
    val s1 = number * 64u
    number = number xor s1
    number %= m

    val s2 = number / 32u
    number = number xor s2
    number %= m

    val s3 = number * 2048u
    number = number xor s3
    number %= m
    return number
}

fun main() {
    val input = readInput("22").map { it.toULong() }
    val result = input.asSequence().map { n ->
        (0 until 2000).fold(n) { acc, _ -> iterate(acc) }
    }.sum()
    println(result)

}