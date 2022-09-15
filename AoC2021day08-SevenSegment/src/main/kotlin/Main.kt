import java.io.File



fun main() {
    val inputFile = File("data.txt")
    val inputLines = inputFile.readLines()
    var result = 0

    for (line in inputLines) {
        val data = line.split(" | ")
        val leftData = data.first().split(" ")
        val rightdata = data.last().split(" ")

        val rosetta = crackCode(leftData)
        val number = translate(rightdata, rosetta)
        result += number
    }
    println(result)
}

private fun crackCode(data: List<String>): MutableMap<Char, Char> {
    val timesUsed = mutableMapOf('a' to 0, 'b' to 0, 'c' to 0, 'd' to 0, 'e' to 0, 'f' to 0, 'g' to 0)
    data.forEach { seq ->
        seq.forEach { c ->
            timesUsed[c] = timesUsed[c]?.plus(1) ?: 0
        }
    }

    val rosetta = mutableMapOf<Char, Char>()

    // segments that are used in a unique number of digits
    rosetta[ timesUsed.entries.filter { it.value == 6 }.first().key ] = '2'
    rosetta[ timesUsed.entries.filter { it.value == 4 }.first().key ] = '5'
    rosetta[ timesUsed.entries.filter { it.value == 9 }.first().key ] = '6'

    // The digit '1' uses segments 3 and 6, so...
    rosetta[ data.filter { it.length == 2 }.first().filter { it !in rosetta.keys }.first() ] = '3'

    // The digit '7' uses segments 3, 6 and 1, so...
    rosetta[ data.filter { it.length == 3 }.first().filter { it !in rosetta.keys }.first() ] = '1'

    // The digit '4' uses segments 2, 3, 6 and 4, so...
    rosetta[ data.filter { it.length == 4 }.first().filter { it !in rosetta.keys }.first() ] = '4'

    // Only rest the segment '7'
    rosetta[ "abcdefg".filter { it !in rosetta.keys }.first() ] = '7'

    return rosetta
}

private fun translate(data: List<String>, rosetta: Map<Char, Char>): Int {
    val segmentsOf = mapOf(
        setOf('1','2','3','5','6','7') to '0',
        setOf('3','6') to '1',
        setOf('1','3','4','5','7') to '2',
        setOf('1','3','4','6','7') to '3',
        setOf('2','3','4','6') to '4',
        setOf('1','2','4','6','7') to '5',
        setOf('1','2','4','5','6','7') to '6',
        setOf('1','3','6') to '7',
        setOf('1','2','3','4','5','6','7') to '8',
        setOf('1','2','3','4','6','7') to '9'
    )
    var result = ""
    for (pattern in data) {
        val thisDigit = mutableSetOf<Char>()
        for (c in pattern) {
            thisDigit.add(rosetta[c]!!)
        }
        result += segmentsOf[thisDigit]
    }

    return result.toInt()
}