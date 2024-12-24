package day23

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

suspend fun main() {
    val connections = readInput()
    val tEntries = connections.entries.filter {
        it.key.startsWith("t")
    }
    val dispatcher = Executors.newFixedThreadPool(8).asCoroutineDispatcher()
    val job = GlobalScope.launch {
        val jobs = tEntries.map { (k, v) ->
            async(dispatcher) {
                sequence {
                    val path = setOf(k)
                    for (step in v) {
                        walkThrough(connections, path, step)
                    }
                }.filter { it.size > 11 }
                    .distinctBy { it.sorted() }
                    .onEach { println(it) }
                    .maxBy { it.size }
            }
        }
        val party = jobs.map { it.await() }.maxBy { it.size }.sorted()
        println(party)
    }
    job.join()
}

suspend fun SequenceScope<Set<String>>.walkThrough(
    connections: Map<String, Set<String>>,
    path: Set<String>,
    step: String,
) {

    val v = connections.getValue(step)
    val similar = path.intersect(v)
    if (similar.size != path.size) return

    val newPath = path + step

    yield(newPath)
    for (j in v.subtract(path)) {
        walkThrough(connections, newPath, j)
    }
}