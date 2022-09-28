import java.io.File

class Paper(lines: Int, cols: Int) {
    init {
        println("A paper was created with $lines lines and $cols cols.")
    }
    val cell = MutableList(lines) { MutableList(cols) { ' ' } }

    fun foldLine(lineToFold: Int): Paper {
        val result = Paper(lineToFold, cell[0].size)
        for (line in 0 until lineToFold) {
            for (col in 0..cell[0].lastIndex) {
                result.cell[line][col] =
                    if (cell[line][col] != ' ' || cell[cell.lastIndex - line][col] != ' ') '*' else ' '
            }
        }
        return result
    }

    fun foldColumn(colToFold: Int): Paper {
        val result = Paper(cell.size, colToFold)
        for (line in 0 .. cell.lastIndex) {
            for (col in 0 until colToFold) {
                result.cell[line][col] =
                    if (cell[line][col] != ' ' || cell[line][cell[0].lastIndex - col] != ' ') '*' else ' '
            }
        }
        return result
    }

    fun display() {
        cell.forEach { println(it) }
    }

    fun countDots() = cell.fold(0, { acc, l -> l.filter { c -> c == '*' }.count() + acc })
}

fun main() {
    val input = File("data.txt").readLines()

    var lines = 0
    var cols = 0
    val points = mutableListOf<Pair<Int, Int>>()
    var inputLineNumber = 0
    while (input[inputLineNumber].isNotEmpty()) {
        val (col, line) = input[inputLineNumber].split(",").map { it.toInt() }
        if (col > cols) {
            cols = col
        }
        if (line > lines) {
            lines = line
        }
        points.add(Pair(col, line))
        inputLineNumber++
    }
    var paper = Paper(lines + 1, cols + 1)
    points.forEach { paper.cell[it.second][it.first] = '*' }

    inputLineNumber++
    while (inputLineNumber <= input.lastIndex) {
        println(input[inputLineNumber])
        val command = input[inputLineNumber].split(' ').last()
        val direction = command[0]
        val value = command.split('=').last().toInt()
        paper = when (direction) {
            'y' -> paper.foldLine(value)
            'x' -> paper.foldColumn(value)
            else -> throw Error()
        }
        println("The number of dots is ${paper.countDots()}.")
        inputLineNumber++
    }
    paper.display()
}