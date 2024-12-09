package day09

import utils.readInput
import java.util.*

fun <K, V> SortedMap<K, V>.find(predicate: (Map.Entry<K, V>) -> Boolean): Map.Entry<K, V>? {
    for (entry in entries) {
        if (predicate(entry)) {
            return entry
        }
    }
    return null
}

fun compact2(diskInfo: DiskInfo): FileSystem = buildList {
    val (idToPosition, positionToEmpty, oldFileSystem) = diskInfo
    addAll(oldFileSystem)

    val ltrPositionToEmpty = positionToEmpty.toSortedMap()
    val reversedIdToPosition = idToPosition.toSortedMap { k1, k2 -> -k1.compareTo(k2) }
    for ((id, info) in reversedIdToPosition) {
        val (fileStartIdx, fileSize) = info
        val (emptyStartIdx, emptySpace) = ltrPositionToEmpty.find { (position, space) ->
            position < fileStartIdx && space >= fileSize
        } ?: continue
        ltrPositionToEmpty.remove(emptyStartIdx)

        for (i in 0 until fileSize) {
            set(emptyStartIdx + i, id)
            set(fileStartIdx + i, null)
        }

        val leftOverSpace = emptySpace - fileSize
        if (leftOverSpace > 0) {
            ltrPositionToEmpty[emptyStartIdx + fileSize] = leftOverSpace
        }
    }
}

fun main() {
    val input = readInput("09").single()
    val info = fileSystemOf(input)
    val compacted = compact2(info)
    println(compacted.joinToString(separator = "") { it?.toString() ?: "." })
    val checkSum = checksumOf(compacted)
    println(checkSum)
}