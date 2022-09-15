import java.io.File
import java.util.*


var numbers: List<Int> = emptyList()
var boards: MutableList<Board> = mutableListOf()

data class Square(var number: Int, var isChecked: Boolean = false)

class Board() {
    var won: Boolean = false
    val numbers = mutableSetOf<Int>()
    val squares = arrayOf(
        arrayOf(Square(0), Square(0), Square(0), Square(0), Square(0)),
        arrayOf(Square(0), Square(0), Square(0), Square(0), Square(0)),
        arrayOf(Square(0), Square(0), Square(0), Square(0), Square(0)),
        arrayOf(Square(0), Square(0), Square(0), Square(0), Square(0)),
        arrayOf(Square(0), Square(0), Square(0), Square(0), Square(0))
    )

    fun printBoard() {
        for (row in squares.indices) {
            for (col in squares[row].indices) {
                print("${squares[row][col].number.toString().format("%d")} ")
            }
            println()
        }
    }

    fun isRowChecked(row: Int): Boolean {
        var allChecked = true
        for (col in 0..4) {
            if (!squares[row][col].isChecked) {
                return false
            }
        }
        return true
    }

    fun isColChecked(col: Int): Boolean {
        var allChecked = true
        for (row in 0..4) {
            if (!squares[row][col].isChecked) {
                return false
            }
        }
        return true
    }

    fun whereIs(n: Int): Pair<Int, Int> {
        for (r in 0..4) {
            for (c in 0..4) {
                if (squares[r][c].number == n) {
                    return Pair(r, c)
                }
            }
        }
        return Pair(0, 0)
    }

    fun unmarkedPoints(): Int {
        var points = 0
        for (r in 0..4) {
            for (c in 0..4) {
                if (!squares[r][c].isChecked) {
                    points += squares[r][c].number
                }
            }
        }
        return points
    }
}

fun main() {
    val input = File("bingoData.txt")
    val scanner = Scanner(input)
    numbers = scanner.nextLine().split(",").map { it.toInt() }

    while (scanner.hasNext()) {
        val board = Board()
        for (row in 0 .. 4) {
            for (col in 0..4) {
                val n = scanner.nextInt()
                board.squares[row][col].number = n
                board.numbers.add(n)
            }
        }
        boards.add(board)
//        board.printBoard()
//        println("=================")
    }

    for (n in numbers) {
        for ((boardIndex, b) in boards.withIndex()) {
            if (b.won) {
                continue
            }
            if (n in b.numbers) {
                val (row, col) = b.whereIs(n)
                b.squares[row][col].isChecked = true
                if (b.isRowChecked(row) || b.isColChecked(col)) {
                    println("Board ${boardIndex + 1} ganhou com um score de ${b.unmarkedPoints() * n}")
                    b.won = true
                    continue
                }
            }
        }
    }
}