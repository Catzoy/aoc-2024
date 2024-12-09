package day09

import utils.readInput

typealias FileSystem = List<Int?>
typealias FileIdToPositionAndSize = Map<Int, Pair<Int, Int>>
typealias PositionToEmptySpace = Map<Int, Int>
typealias DiskInfo = Triple<FileIdToPositionAndSize, PositionToEmptySpace, FileSystem>

fun fileSystemOf(line: String): DiskInfo {
    val idToFile = mutableMapOf<Int, Pair<Int, Int>>()
    val positionToEmpty = mutableMapOf<Int, Int>()
    val fileSystem = buildList {
        var id = -1
        var isFile = true
        for (entitySize in line.asSequence().map { it.digitToInt() }) {
            if (isFile) {
                id++
                idToFile[id] = size to entitySize
                addAll(List(entitySize) { id })
                isFile = false
            } else {
                positionToEmpty[size] = entitySize
                addAll(List(entitySize) { null })
                isFile = true
            }
        }
    }
    return Triple(
        idToFile,
        positionToEmpty,
        fileSystem,
    )
}

fun compact(oldFileSystem: FileSystem): FileSystem = buildList {
    addAll(oldFileSystem)

    var firstEmpty = indexOf(null)
    var lastFilled = indexOfLast { it != null }
    while (firstEmpty < lastFilled) {
        set(firstEmpty, get(lastFilled))
        set(lastFilled, null)
        firstEmpty = indexOf(null)
        lastFilled = indexOfLast { it != null }
    }
}

fun checksumOf(fileSystem: FileSystem): Long =
    fileSystem.asSequence()
        .map { it?.toLong() }
        .withIndex()
        .sumOf { (i, id) -> i * (id ?: 0) }

fun main() {
    val input = readInput("09").single()
    val (_, _, fileSystem) = fileSystemOf(input)
    val compacted = compact(fileSystem)
    val checkSum = checksumOf(compacted)
    println(checkSum)
}