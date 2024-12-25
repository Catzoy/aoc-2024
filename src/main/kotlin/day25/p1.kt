package day25

fun readInput(): Pair<List<List<Int>>, List<List<Int>>> {
    val keys = mutableListOf<List<Int>>()
    val locks = mutableListOf<List<Int>>()

    val prints = utils.readInput("25")
        .asSequence()
        .chunked(8)
    for (print in prints) {
        when (print.first()) {
            "#####" -> {
                val lock = buildList {
                    for (x in 0 until 5) {
                        var height = 0
                        for (y in 1 until 6) {
                            if (print[y][x] == '.') {
                                break
                            } else {
                                height++
                            }
                        }
                        add(height)
                    }
                }
                locks.add(lock)
            }

            "....." -> {
                val key = buildList {
                    for (x in 0 until 5) {
                        var height = 5
                        for (y in 1 until 6) {
                            if (print[y][x] == '#') {
                                break
                            } else {
                                height--
                            }
                        }
                        add(height)
                    }
                }
                keys.add(key)
            }
        }
    }
    return Pair(keys, locks)
}

typealias Tree = MutableMap<Int, MutableMap<Int, MutableMap<Int, MutableMap<Int, MutableMap<Int, Int>>>>>

fun main() {
    val (keys, locks) = readInput()
    val root: Tree = mutableMapOf()
    for ((k1, k2, k3, k4, k5) in keys) {
        val b1 = root.getOrPut(k1) { mutableMapOf() }
        val b2 = b1.getOrPut(k2) { mutableMapOf() }
        val b3 = b2.getOrPut(k3) { mutableMapOf() }
        val b4 = b3.getOrPut(k4) { mutableMapOf() }
        b4[k5] = 1
    }
    var pairs = 0
    for (lock in locks) {
        val (l1, l2, l3, l4, l5) = lock
        val p1 = 0..<(6 - l1)
        val p2 = 0..<(6 - l2)
        val p3 = 0..<(6 - l3)
        val p4 = 0..<(6 - l4)
        val p5 = 0..<(6 - l5)

        val b1s = root.filterKeys { k1 -> k1 in p1 }.values
        val b2s = b1s.flatMap { b1 -> b1.filterKeys { k2 -> k2 in p2 }.values }
        val b3s = b2s.flatMap { b2 -> b2.filterKeys { k3 -> k3 in p3 }.values }
        val b4s = b3s.flatMap { b3 -> b3.filterKeys { k4 -> k4 in p4 }.values }
        val b5s = b4s.flatMap { b4 -> b4.filterKeys { k5 -> k5 in p5 }.values }
        println("LOCK $lock has ${b5s.sum()} keys")
        pairs += b5s.sum()
    }
    println(pairs)
}