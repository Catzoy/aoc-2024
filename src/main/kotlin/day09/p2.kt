package day09

import utils.readInput

fun compact2(diskInfo: DiskInfo): FileSystem = buildList {
    val (idToPosition, positionToEmpty, oldFileSystem) = diskInfo
    addAll(oldFileSystem)

    val ltrPositionToEmpty = positionToEmpty.toSortedMap()
    val reversedIdToPosition = idToPosition.toSortedMap { k1, k2 -> -k1.compareTo(k2) }
    for ((id, info) in reversedIdToPosition) {
        val (fileStartIdx, fileSize) = info
        var emptySpaceIdx = -1
        for ((emptyPosition, emptySize) in ltrPositionToEmpty) {
            if (emptyPosition > fileStartIdx) {
                break
            }
            if (emptySize >= fileSize) {
                emptySpaceIdx = emptyPosition
                break
            }
        }
        if (emptySpaceIdx == -1) {
            continue
        }

        val emptySpace = ltrPositionToEmpty.remove(emptySpaceIdx)!!
        for (i in 0 until fileSize) {
            set(emptySpaceIdx + i, id)
            set(fileStartIdx + i, null)
        }

        val leftOverSpace = emptySpace - fileSize
        if (leftOverSpace > 0) {
            ltrPositionToEmpty[emptySpaceIdx + fileSize] = leftOverSpace
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