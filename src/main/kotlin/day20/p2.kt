package day20

fun main() {
    val (s, e, matrix) = readInput()
    val visited = walk(s, e, matrix)
    val edgeMatrix = edgesOf(point = 0 to 0, n = 20)
    val cheats = findCheats(visited, matrix, edgeMatrix)
    val applicable = cheats
        .filter { it.key >= 100 }
        .values
        .sumOf { it.size }
    println(applicable)
}