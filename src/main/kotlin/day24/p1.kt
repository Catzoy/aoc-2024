package day24

typealias Registers = MutableMap<String, Int>
typealias Gate = Pair<String, Triple<String, String, String>>

fun readInput(): Pair<Registers, List<Gate>> {
    val start = Regex("(.+): (\\d+)")
    val gate = Regex("(.+) (.+) (.+) -> (.+)")
    val input = utils.readInput("24").iterator()
    var line = input.next()
    val starters = mutableMapOf<String, Int>().apply {
        while (line.isNotEmpty()) {
            val (name, value) = start.find(line)!!.destructured
            put(name, value.toInt())
            line = input.next()
        }
    }

    val gates = buildList {
        while (input.hasNext()) {
            line = input.next()
            val (a, act, b, c) = gate.find(line)!!.destructured
            add(act to Triple(a, b, c))
        }
    }

    return (starters to gates)
}

fun main() {
    val (registers, gates) = readInput()
    val processingQueue = gates.foldIndexed(mutableListOf<Int>()) { i, acc, (_, wires) ->
        val (i1, i2) = wires
        if (registers.containsKey(i1) && registers.containsKey(i2)) {
            acc.add(i)
        }
        acc
    }
    while (processingQueue.isNotEmpty()) {
        val gateIdx = processingQueue.removeFirst()
        val (act, wires) = gates[gateIdx]
        val (i1, i2, o) = wires
        registers[o] = when (act) {
            "AND" -> registers[i1]!! and registers[i2]!!
            "XOR" -> registers[i1]!! xor registers[i2]!!
            "OR" -> registers[i1]!! or registers[i2]!!
            else -> throw IllegalArgumentException("Unknown action: $act")
        }
        for ((g, gate) in gates.withIndex()) {
            val (gi1, gi2) = gate.second
            if (gi1 == o && registers.containsKey(gi2)) {
                processingQueue.add(g)
            } else if (gi2 == o && registers.containsKey(gi1)) {
                processingQueue.add(g)
            }
        }
    }
    val result = registers.entries.asSequence()
        .filter { it.key.startsWith("z") }
        .sortedByDescending { it.key }
        .map { it.value }
        .joinToString("")
        .toLong(radix = 2)
    println(result)
}