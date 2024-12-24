package day22

import utils.readInput

typealias PriceWithCombo = Pair<Int, List<Int>>

fun generateIterations(n: ULong): Sequence<ULong> {
    return sequence {
        var p = n
        yield(p)
        for (i in 0 until 2000) {
            p = iterate(p)
            yield(p)
        }
    }
}

fun main() {
    val input = readInput("22").map { it.toULong() }
    val result = input.asSequence().map { n ->
        val x = generateIterations(n)
            .map { (it % 10u).toInt() }
            .zipWithNext()
            .map { (a, b) -> b to (b - a) }
        sequence<PriceWithCombo> {
            val combo = mutableListOf<Int>()
            for ((price, diff) in x) {
                combo.add(diff)
                if (combo.size == 4) {
                    yield(price to combo.toList())
                    combo.removeAt(0)
                }
            }
        }.distinctBy { (_, c) -> c.joinToString(",") }
    }.fold(mutableMapOf<String, Long>()) { acc, sequence ->
        for ((price, combo) in sequence) {
            acc.merge(combo.joinToString(","), price.toLong()) { p1, p2 -> p1 + p2 }
        }
        acc
    }.maxBy { it.value }.value
    println(result)

}