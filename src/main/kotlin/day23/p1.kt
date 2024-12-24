package day23

fun readInput(): Map<String, Set<String>> {
    val pair = Regex("(.+)-(.+)")
    return utils.readInput("23").map { line ->
        val (a, b) = pair.find(line)!!.destructured
        a to b
    }.fold(mutableMapOf()) { acc, (a, b) ->
        acc.apply {
            merge(a, setOf(b)) { s1, s2 -> s1 + s2 }
            merge(b, setOf(a)) { s1, s2 -> s1 + s2 }
        }
    }
}

fun main() {
    val connections = readInput()
    val parties = connections.entries.asSequence()
        .filter { it.key.startsWith("t") }
        .flatMap { (k, v) ->
            val path = setOf(k)
            v.flatMap { step ->
                walkInto(connections, path, step, len = 3)
            }
        }
        .distinctBy { it.sorted() }
        .count()
    println(parties)
}

fun walkInto(
    connections: Map<String, Set<String>>,
    path: Set<String>,
    step: String,
    len: Int,
): List<Set<String>> {
    if (path.size == len) return listOf(path)

    val v = connections.getValue(step)
    val similar = path.intersect(v)
    if (similar.size != path.size) return emptyList()

    val newPath = path + step
    if (newPath.size == len) return listOf(newPath)

    return v.subtract(path).flatMap { j ->
        walkInto(connections, newPath, j, len)
    }
}
