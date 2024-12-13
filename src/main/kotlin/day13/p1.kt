package day13

import utils.Point
import utils.readInput

data class Machine(
    val a: Pair<Int, Int>,
    val b: Pair<Int, Int>,
    val prize: Point,
)

fun readInput(): List<Machine> {
    val buttonA = Regex("Button A: X\\+(\\d+), Y\\+(\\d+)")
    val buttonB = Regex("Button B: X\\+(\\d+), Y\\+(\\d+)")
    val prize = Regex("Prize: X=(\\d+), Y=(\\d+)")
    val regexs = listOf(buttonA, buttonB, prize)
    return readInput("13")
        .chunked(4)
        .map { lines ->
            val matches = lines.zip(regexs).map { (line, regex) ->
                regex.find(line)!!.groupValues.drop(1).map { it.toInt() }
            }
            Machine(
                a = matches[0][0] to matches[0][1],
                b = matches[1][0] to matches[1][1],
                prize = matches[2][0] to matches[2][1]
            )
        }
}

fun solutions(num1: Int, num2: Int, target: Int): Set<Pair<Int, Int>> {
    return buildSet {
        var i = 0
        while (i * num1 <= target) {
            val remaining = target - i * num1
            if (remaining % num2 == 0) {
                add(i to remaining / num2)
            }
            i++
        }
    }
}

fun solve(machines: List<Machine>): Int {
    return machines.sumOf { machine ->
        val xs = solutions(machine.a.first, machine.b.first, machine.prize.first)
        val ys = solutions(machine.a.second, machine.b.second, machine.prize.second)
        val common = xs.intersect(ys).sortedBy { it.first + it.second }
        common.firstOrNull()?.let { (nX, nY) -> 3 * nX + nY } ?: 0
    }
}

fun main() {
    val machines = readInput()
    val result = solve(machines)
    println(result)
}