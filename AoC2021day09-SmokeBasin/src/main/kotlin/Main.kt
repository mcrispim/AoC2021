import java.io.File

class Basin(val points: MutableSet<Pair<Int, Int>> = mutableSetOf())

fun main() {
    val input = File("data.txt")
    val gridChar = input.readLines()
    val grid = readInts(gridChar)
    val lowPoints: List<Pair<Int, Int>> = getLowPoints(grid)
    println ("The sum of the risk levels of all low points is ${riskLevel(lowPoints, grid)}")

    val basins: List<Basin> = getBasins(lowPoints, grid)
    val basinsSizes = basins.map { it.points.size }
    val product = basins.map { it.points.size }.sorted().takeLast(3).fold(1) { acc, n -> acc * n}
    println("The product of the size of the 3 biggest basins is $product")
}

fun getBasins(lowPoints: List<Pair<Int, Int>>, grid: MutableList<IntArray>): MutableList<Basin> {
    val basins = mutableListOf<Basin>()
    for ((line, col) in lowPoints) {
        val basin = Basin(mutableSetOf(Pair(line, col)))
        val pointsToCheck: MutableList<Pair<Int, Int>> = getNextPoints(line, col, grid).toMutableList()
        while (pointsToCheck.isNotEmpty()) {
            val point = pointsToCheck.removeFirst()
            if (grid[point.first][point.second] != 9 && point !in basin.points) {
                basin.points.add(point)
                pointsToCheck.addAll(getNextPoints(point.first, point.second, grid))
            }
        }
        basins.add(basin)
    }
    return basins
}

fun getNextPoints(line: Int, col: Int, grid: MutableList<IntArray>): List<Pair<Int, Int>> {
    val points = mutableListOf<Pair<Int, Int>>()
    if (line > 0) {
        points.add(Pair(line - 1, col))
    }
    if (line < grid.lastIndex) {
        points.add(Pair(line + 1, col))
    }
    if (col > 0) {
        points.add(Pair(line, col - 1))
    }
    if (col < grid[line].lastIndex) {
        points.add(Pair(line, col + 1))
    }
    return points
}

private fun getLowPoints(grid: MutableList<IntArray>): List<Pair<Int, Int>> {
    val result = mutableListOf<Pair<Int, Int>>()
    for (line in grid.indices) {
        for (col in grid[line].indices) {
            if (isLowPoint(grid, line, col)) {
                result.add(Pair(line, col))
            }
        }
    }
    return result
}

private fun readInts(gridChar: List<String>): MutableList<IntArray> {
    val grid = mutableListOf<IntArray>()
    for (line in gridChar) {
        val myLine = mutableListOf<Int>()
        for (c in line) {
            myLine.add(c.digitToInt())
        }
        grid.add(myLine.toIntArray())
    }
    return grid
}

private fun riskLevel(lowPoints: List<Pair<Int, Int>>, grid: List<IntArray>): Int {
    var result = 0
    for ((line, col) in lowPoints) {
        val value = grid[line][col]
        result += (value + 1)
    }
    return result
}

private fun isLowPoint(board: List<IntArray>, line: Int, col: Int): Boolean {
    val value = board[line][col]
    if (line > 0 && value >= board[line - 1][col]) {
        return false
    }
    if (line < board.lastIndex && value >= board[line + 1][col]) {
        return false
    }
    if (col > 0 && value >= board[line][col - 1]) {
        return false
    }
    if (col < board[line].lastIndex && value >= board[line][col + 1]) {
        return false
    }
    return true
}