package day04

import utils.readInput

fun Pair<Int, Int>.xmasSequence(dx: Int, dy: Int): Sequence<Pair<Int, Int>> {
    return sequence {
        var (x, y) = this@xmasSequence
        repeat(4) {
            yield(Pair(x, y))
            x += dx
            y += dy
        }
    }
}

fun Pair<Int, Int>.xmasSequences(): Sequence<Sequence<Pair<Int, Int>>> {
    return sequence {
        yield(xmasSequence(-1, 1)) // left down
        yield(xmasSequence(-1, 0)) // left
        yield(xmasSequence(-1, -1)) // left up
        yield(xmasSequence(0, -1)) // up
        yield(xmasSequence(1, -1)) // right up
        yield(xmasSequence(1, 0)) // right
        yield(xmasSequence(1, 1)) // right down
        yield(xmasSequence(0, 1)) // down
    }
}

fun Sequence<Pair<Int, Int>>.toWord(lines: List<CharArray>): String {
    return this.map { (x, y) ->
        lines.getOrNull(x)?.getOrNull(y)
    }.joinToString("")
}

fun main() {
    val input = readInput("04")
    val lines = input.map { it.toCharArray() }
    val result = lines.withIndex()
        .sumOf { (i, line) ->
            line.withIndex()
                .filter { (_, c) -> c == 'X' }
                .sumOf { (j, _) ->
                    Pair(i, j).xmasSequences()
                        .map { neighbours -> neighbours.toWord(lines) }
                        .count { it == "XMAS" }
                }
        }
    print(result)
}