import java.io.File

lateinit var cave: List<List<Int>>
data class Position(val x: Int, val y: Int)

fun main() {
    val originalCave = File("data.txt").readLines().map { str -> str.map { c-> c.digitToInt() } }
// For the first part, cave = originalCave.
// For the second part, cave = growCave(originalCave)
    cave = growCave(originalCave)
    val lastRow = cave.lastIndex
    val lastCol = cave[0].lastIndex
    val positionsToLook = mutableListOf<Position>()
    val cameFrom = mutableMapOf<Position, Position?>()
    val untilHere = mutableMapOf<Position, Int>()
    val visited = mutableListOf<Position>()

    val start = Position(0, 0)
    val end = Position(lastRow, lastCol)
    cameFrom[start] = null
    untilHere[start] = 0

    positionsToLook.add(start)
    visited.add(start)

    while (positionsToLook.isNotEmpty()) {
        println(positionsToLook.size)
        val current = positionsToLook.removeFirst()
        for (nextPosition in neighbours(current, lastRow, lastCol, visited)) {
            val untilNow = untilHere[current]?.plus(cave[nextPosition.x][nextPosition.y])
            if (cameFrom[nextPosition] == null || untilHere[nextPosition]!! > untilNow!!) {
                cameFrom[nextPosition] = current
                untilHere[nextPosition] = untilNow!!
                positionsToLook.add(nextPosition)
            }
        }
        visited.add(current)
    }
    println("Risk: ${untilHere[end]}")
}

fun growCave(originalCave: List<List<Int>>): List<List<Int>> {
    val bigCave = mutableListOf<MutableList<Int>>()
    val nLines = originalCave.size
    val nCols = originalCave[0].size
    for (line in originalCave) {
        bigCave.add(line.toMutableList())
    }
    repeat(4 * nCols) {
        for (line in bigCave) {
            val n = line[line.size - 10]
            line.add(if (n < 9) n + 1 else 1)
        }
    }
    repeat(4 * nLines) {
        val line = mutableListOf<Int>()
        for (n in bigCave[bigCave.size - 10]) {
            line.add(if (n < 9) n + 1 else 1)
        }
        bigCave.add(line)
    }
    return bigCave
}

fun neighbours(
    xy: Position,
    maxLines: Int = Int.MAX_VALUE,
    maxCols: Int = Int.MAX_VALUE,
    visited: MutableList<Position>
): List<Position> {
    val (line, col) = xy
    val positions = mutableListOf<Position>(
        Position(line - 1, col),
        Position(line, col - 1),
        Position(line, col + 1),
        Position(line + 1, col)
    )
    return positions.filter { p -> p.x in 0..maxLines && p.y in 0..maxCols && p !in visited}
}
