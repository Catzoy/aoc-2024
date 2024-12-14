package day14

import java.io.File


fun main() {
    val (wide, tall) = 101 to 103
    val robots = readInput()
    var counter = 0
    /// 30 - first time a horizontal `frame` appears
    /// 103 - each time a new horizontal `frame` appears
    /// 10401 - last unique combination of robots
    val secs = generateSequence {
        val s = 30L + 103 * counter++
        if (s < 10401) s else null
    }
    val file = File("src/main/kotlin/day14/results3.txt")
    file.outputStream().use { stream ->
        stream.writer().use { writer ->
            for (seconds in secs) {
                writer.appendLine("SECONDS - $seconds")
                val room = simulate(
                    dimensions = (wide to tall),
                    seconds = seconds,
                    robots = robots,
                )
                for (row in room) {
                    val str = row.joinToString("") {
                        when {
                            it.isEmpty() -> "."
                            else -> "#"
                        }
                    }
                    writer.appendLine(str)
                }
                writer.appendLine()
            }
        }
    }
}