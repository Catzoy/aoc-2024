package day06

import utils.readInput

fun List<String>.toWalkable() = toList().map { it.toCharArray() }

fun List<CharArray>.walk(
    override: Pair<Int, Int>? = null,
): List<CharArray>? = apply {
    if (override != null) {
        val (oY, oX) = override
        if (oY !in indices) throw Exception("Invalid override")
        if (oX !in this[oY].indices) throw Exception("Invalid override")
        this[oY][oX] = '#'
    }

    var (y, x) = asSequence()
        .mapIndexed { index, chars -> index to chars.indexOf('^') }
        .firstOrNull { (_, x) -> x != -1 }
        ?: throw Exception("Cannot start")

    var isWalking = true
    var didLoop = false
    val visits = mutableMapOf<Pair<Int, Int>, MutableSet<Char>>()
    do {
        val direction = this[y][x]
        val visitedBy = visits.getOrPut((y to x)) { mutableSetOf() }
        val didVisit = !visitedBy.add(direction)
        if (didVisit) {
            didLoop = true
            break
        }

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
    if (didLoop) {
        return null
    }
}

fun main() {
    val result = readInput("06")
        .toWalkable()
        .walk()!!
        .sumOf { arr -> arr.count { it == 'X' } }
    print(result)
}