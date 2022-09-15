import java.io.File
import java.util.Scanner

fun main() {
    val points = mutableMapOf<Pair<Int, Int>, Int>()
    val input = File("lines.txt")
    val scanner = Scanner(input)
    var lineNumber = 0
    while (scanner.hasNext()) {
        val line = scanner.nextLine()
        println(line)
        lineNumber++
        val (fromStr, toStr) = line.split(" -> ")
        var (x1, y1) = fromStr.split(",").map { it.toInt() }
        var (x2, y2) = toStr.split(",").map { it.toInt() }
        if (x1 == x2) {
            if (y1 > y2) {
                val temp = y1
                y1 = y2
                y2 = temp
            }
            for (y in y1..y2) {
                points[Pair(x1, y)] = if (points[Pair(x1, y)] == null) 1 else points[Pair(x1, y)]!! + 1
//                print("[$x1,$y] ")
            }
//            println()
        } else if (y1 == y2) {
            if (x1 > x2) {
                val temp = x1
                x1 = x2
                x2 = temp
            }
            for (x in x1..x2) {
                points[Pair(x, y1)] = if (points[Pair(x, y1)] == null) 1 else points[Pair(x, y1)]!! + 1
//                print("[$x,$y1] ")
            }
//            println()
        } else {
            if (x1 > x2) {
                val tempX = x1
                val tempY = y1
                x1 = x2
                y1 = y2
                x2 = tempX
                y2 = tempY
            }
            val inc = if (y1 < y2) 1 else -1
            var y = y1
            for (x in x1..x2) {
                points[Pair(x, y)] = if (points[Pair(x, y)] == null) 1 else points[Pair(x, y)]!! + 1
                y += inc
            }
        }
    }

    println("${points.values.filter { it > 1 }.count()}")
}