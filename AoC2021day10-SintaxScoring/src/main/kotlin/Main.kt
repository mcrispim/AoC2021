import java.io.File

val closeChar = mapOf(')' to '(', ']' to '[', '}' to '{', '>' to '<')
val openChar = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')
val openings = closeChar.values

enum class LineType {
    CORRUPTED,
    INCOMPLETE,
    CORRECT,
}

class Line(val type: LineType, val problemChars: List<Char>)

fun main() {
    val inputData = File("data.txt")
    val data = inputData.readLines()
    val linesCorrupted = mutableListOf<Line>()
    val linesIncomplete = mutableListOf<Line>()
    val linesCorrect = mutableListOf<Line>()

    for (line in data) {
        val myLine = processLine(line)
        when (myLine.type) {
            LineType.CORRUPTED -> linesCorrupted.add(myLine)
            LineType.INCOMPLETE -> linesIncomplete.add(myLine)
            LineType.CORRECT -> linesCorrect.add(myLine)
        }
    }
    println("From ${data.size} lines, ${linesCorrupted.size} were CORRUPTED, ${linesIncomplete.size} were INCOMPLETE and ${linesCorrect.size} where ok.")

    var corruptionScore = 0L
    for (line in linesCorrupted) {
        val c = line.problemChars.first()
        corruptionScore += wrongCharValue(c)
    }
    println("The score is $corruptionScore.")

    val incompleteScores = mutableListOf<Long>()
    for (line in linesIncomplete) {
        var score = 0L
        for (c in line.problemChars.asReversed()) {
            score = score * 5 + missingCharValue(openChar[c]!!)
        }
        incompleteScores.add(score)
    }
    val middleScore = incompleteScores.sorted()[incompleteScores.size / 2]
    println("The middle incomplete score is $middleScore.")
}

fun processLine(line: String): Line {
    val stack = mutableListOf<Char>()
    for (c in line) {
        if (c in openings) {
            stack.add(c)
        } else {        // it's a closing
            if (stack.isNotEmpty() && stack.last() == closeChar[c]) {
                stack.removeLast()
            } else {                // corrupted Line
                return Line(LineType.CORRUPTED, listOf(c))
            }
        }
    }
    return if (stack.isNotEmpty()) Line(LineType.INCOMPLETE, stack) else Line(LineType.CORRECT, emptyList())
}

fun wrongCharValue(c: Char): Int {
    return when(c) {
        ')' -> 3
        ']' -> 57
        '}' -> 1197
        '>' -> 25137
        else -> 0
    }
}

fun missingCharValue(c: Char): Int {
    return when(c) {
        ')' -> 1
        ']' -> 2
        '}' -> 3
        '>' -> 4
        else -> 0
    }
}