package day18

fun main() {
    val grid = MutableList(71) { MutableList(71) { '.' } }
    val points = readInput()
    val start = (0 to 0)
    val target = (70 to 70)
    for (n in points.indices.drop(1024)) {
        val affected = points.take(n)
        println("PROCESSING ${affected.size} first points")
        val affectedGrid = grid.map { it.toMutableList() }.toMutableList()
        affected.forEach { (y, x) ->
            affectedGrid[y][x] = '#'
        }
        val representation = aStarFinding(
            matrix = affectedGrid,
            start = start,
            end = target,
        )
        val resulting = representation.last()
        if (resulting.point != target) {
            val (y, x) = affected.last()
            println("Failed to reach target at ${x},${y}")
            break
        }
    }
}