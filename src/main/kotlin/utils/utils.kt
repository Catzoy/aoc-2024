package utils

import java.io.File
import kotlin.math.absoluteValue

fun readInput(day: String): List<String> {
    return File("src/main/kotlin/day$day/input.txt")
        .readLines()
}

val dLeft: Direction = (0 to -1)
val dRight: Direction = (0 to 1)
val dUp: Direction = (-1 to 0)
val dDown: Direction = (1 to 0)
val directions = listOf(
    dLeft, dUp, dRight, dDown
)

typealias Point = Pair<Int, Int>
typealias Direction = Pair<Int, Int>

@JvmName("plusIntPoint")
operator fun Point.plus(other: Point): Point {
    return first + other.first to second + other.second
}

@JvmName("plusLongPoint")
operator fun Pair<Long, Long>.plus(other: Pair<Long, Long>): Pair<Long, Long> {
    return first + other.first to second + other.second
}

operator fun Point.minus(other: Point): Point {
    return first - other.first to second - other.second
}

val Direction.adjacent
    get() = when (this) {
        (-1 to 0), (1 to 0) -> listOf((0 to -1), (0 to 1))
        (0 to -1), (0 to 1) -> listOf((-1 to 0), (1 to 0))
        else -> error("Invalid direction")
    }

fun Direction.toChar(): Char {
    return when (this) {
        dLeft -> '<'
        dRight -> '>'
        dUp -> '^'
        dDown -> 'v'
        else -> throw Exception("Unknown direction $this")
    }
}

fun Iterable<CharSequence>.indicesOf(element: Char): Pair<Int, Int> {
    return asSequence()
        .flatMapIndexed { y, row ->
            row.asSequence()
                .mapIndexedNotNull { x, e ->
                    if (e == element) y to x else null
                }
        }
        .first()
}

fun Point.manhattan(other: Point): Int {
    return (first - other.first).absoluteValue +
            (second - other.second).absoluteValue
}