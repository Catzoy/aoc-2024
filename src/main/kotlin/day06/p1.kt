package day06

import utils.readInput

fun List<CharArray>.walk(): List<CharArray> = apply {
    var (y, x) = asSequence()
        .mapIndexed { index, chars -> index to chars.indexOf('^') }
        .first { (_, x) -> x != -1 }

    var isWalking = true
    do {
        val direction = this[y][x]

        val (nY, nX) = when (direction) {
            '^' -> y - 1 to x
            '>' -> y to x + 1
            'v' -> y + 1 to x
            '<' -> y to x - 1
            else -> throw Exception("Unsupported char")
        }
        val next = getOrNull(nY)?.getOrNull(nX)
        when (next) {
            null -> {
                this[y][x] = 'X'
                isWalking = false
            }

            '#' -> {
                val rotated = when (direction) {
                    '^' -> '>'
                    '>' -> 'v'
                    'v' -> '<'
                    '<' -> '^'
                    else -> throw Exception("Unsupported char")
                }
                this[y][x] = rotated
            }

            else -> {
                this[y][x] = 'X'
                this[nY][nX] = direction
                y = nY
                x = nX
            }
        }
    } while (isWalking)
}

fun main() {
    val result = readInput("06")
        .map { it.toCharArray() }
        .walk()
        .also { map ->
            for (line in map) {
                println(String(line))
            }
        }
        .sumOf { arr -> arr.count { it == 'X' } }
    print(result)
}