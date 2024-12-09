package day09

import utils.readInput

fun fileSystemOf(line: String): List<Int?> = buildList {
    var id = -1
    var isFile = true
    for (char in line.asSequence().map { it.digitToInt() }) {
        if (isFile) {
            id++
            addAll(List(char) { id })
            isFile = false
        } else {
            addAll(List(char) { null })
            isFile = true
        }
    }
}

fun compact(fileSystem: List<Int?>): List<Int?> = buildList {
    addAll(fileSystem)

    var firstEmpty = indexOf(null)
    var lastFilled = indexOfLast { it != null }
    while (firstEmpty < lastFilled) {
        set(firstEmpty, get(lastFilled))
        set(lastFilled, null)
        firstEmpty = indexOf(null)
        lastFilled = indexOfLast { it != null }
    }
}

fun checksumOf(fileSystem: List<Int?>): Long =
    fileSystem.asSequence()
        .filterNotNull()
        .map { it.toLong() }
        .withIndex()
        .sumOf { (i, id) -> i * id }

fun main() {
    val input = readInput("09").single()
    val fileSystem = fileSystemOf(input)
    val compacted = compact(fileSystem)
    val checkSum = checksumOf(compacted)
    println(checkSum)
}