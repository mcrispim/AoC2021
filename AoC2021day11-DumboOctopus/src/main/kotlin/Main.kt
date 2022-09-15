import java.io.File

const val LINES = 10
const val COLS = 10
const val STEPS = 100

fun main() {
    val input = File("data.txt").readLines()
    val grid = MutableList(LINES) { line -> MutableList(COLS) { col -> input[line][col].digitToInt() } }

    var nFlashes = 0
    var totalFlashes = 0
    var step = 0
    while (nFlashes != LINES * COLS) {
        processStep(grid)
        step += 1
        nFlashes = flashGrid(grid)
        totalFlashes += nFlashes
        if (step == STEPS) {
            println("In $STEPS steps ocurred $totalFlashes flashes.")
        }
    }
    println("All the octopus flashed at step $step.")
}

fun processStep(grid: MutableList<MutableList<Int>>) {
    for(l in grid.indices) {
        for (c in grid[l].indices) {
            increaseEnergy(grid, l, c)
        }
    }
    var flashed = true
    while (flashed) {
        flashed = false
        for (l in grid.indices) {
            for (c in grid[l].indices) {
                if (grid[l][c] > 9) {
                    flashAndExpand(grid, l, c)
                    flashed = true

                }
            }
        }
    }

}

fun increaseEnergy(grid: MutableList<MutableList<Int>>, l: Int, c: Int) {
    grid[l][c] = grid[l][c] + 1
}

fun flashAndExpand(grid: MutableList<MutableList<Int>>, line: Int, col: Int) {
    grid[line][col] = -1
    val cells = listOf( Pair(-1, -1), Pair(-1, 0),  Pair(-1, 1),
                        Pair( 0, -1),               Pair( 0, 1),
                        Pair( 1, -1), Pair( 1, 0),  Pair( 1, 1))
    for ((l, c) in cells) {
        if (line + l in 0 until LINES && col + c in 0 until COLS && grid[line + l][col + c] != -1) {
                increaseEnergy(grid, line + l, col + c)
        }
    }
}

fun flashGrid(grid: MutableList<MutableList<Int>>): Int {
    var flashes = 0
    for(l in grid.indices) {
        for (c in grid[l].indices) {
            if (grid[l][c] == -1) {
                grid[l][c] = 0
                flashes += 1
            }
        }
    }
    return flashes
}
