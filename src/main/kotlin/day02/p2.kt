package day02

import utils.readInput
import kotlin.math.absoluteValue

fun main() {
    val validDifference = 1..3
    val input = readInput(day = "02")
    val result = input.asSequence()
        .map { line ->
            line.split(" ").map { it.toInt() }
        }
        .map { list ->
            sequence {
                for (i in list.indices) {
                    yield(list.toMutableList().apply { removeAt(i) })
                }
            }
        }
        .map { permutations ->
            permutations
                .map { list ->
                    list.asSequence()
                        .zipWithNext()
                        .map { (e1, e2) -> e1 - e2 }
                        .zipWithNext()
                        .map { (diff1, diff2) ->
                            when {
                                diff1 < 0 && diff2 < 0 ->
                                    diff1.absoluteValue in validDifference
                                            && diff2.absoluteValue in validDifference

                                diff1 > 0 && diff2 > 0 ->
                                    diff1 in validDifference
                                            && diff2 in validDifference

                                else -> false
                            }
                        }
                        .all { areConstraintsSatisfied ->
                            areConstraintsSatisfied
                        }
                }
                .any { isValidPermutation ->
                    isValidPermutation
                }
        }
        .count { hasValidSequence ->
            hasValidSequence
        }
    print(result)
}