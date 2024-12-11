package day11


fun main() {
    var counts = readInput()
    repeat(75) {
        counts = iterate(counts)
    }
    println(counts.values.sum())
}