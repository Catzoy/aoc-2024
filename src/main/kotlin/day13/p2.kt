package day13

import utils.plus

fun solve2(machines: List<Machine>): Long {
    return machines.sumOf { (a, b, prize) ->
        val modifiedPrizeX = prize.first * b.second
        val modifiedPrizeY = prize.second * b.first
        val modifiedAX = a.first * b.second
        val modifiedAY = a.second * b.first
        val diffPrize = modifiedPrizeX - modifiedPrizeY
        val diffA = modifiedAX - modifiedAY
        if (diffPrize % diffA == 0L) {
            val solvedA = diffPrize / diffA
            val solvedB = (prize.first - a.first * solvedA) / b.first
            3 * solvedA + solvedB
        } else {
            0
        }
    }
}

fun main() {
    val alteration = (10000000000000L to 10000000000000L)
    val machines = readInput().map { machine ->
        Machine(
            a = machine.a,
            b = machine.b,
            prize = machine.prize + alteration
        )
    }
    val result = solve2(machines)
    println(result)
}