import java.io.File

fun main() {
    val input = File("diagnostic.txt")
    val lines = input.readLines()
    val nBits = lines[0].length
    val counters = IntArray(nBits)
    for (l in lines) {
        for (i in l.indices) {
            if (l[i] == '1') {
                counters[i]++
            }
        }
    }

    var gammaTxt = ""
    var epsilonTxt = ""
    for (i in 0 until nBits) {
        if (counters[i] > lines.size / 2) {
            gammaTxt += "1"
            epsilonTxt += "0"
        } else {
            gammaTxt += "0"
            epsilonTxt += "1"
        }
    }
    val gamma = gammaTxt.toInt(2)
    val epsilon = epsilonTxt.toInt(2)

    println("Gamma($gamma) * Epsilon($epsilon) = ${gamma * epsilon}")
    println("==============================")

    // oxygen generator rating
    var i = 0
    var tempLines = lines
    while (tempLines.size > 1) {
        val ones = tempLines.filter { line -> line[i] == '1' }.count()
        tempLines = tempLines.filter { line -> line[i] == if (ones >= tempLines.size / 2) '1' else '0' }
        i++
    }
    val oxigenRate = tempLines[0].toInt(2)
    println("oxygen generator rating: $oxigenRate")

    // CO2 scrubber rating
    i = 0
    tempLines = lines
    while (tempLines.size > 1) {
        val ones = tempLines.filter { line -> line[i] == '1' }.count()
        tempLines = tempLines.filter { line -> line[i] == if (ones >= tempLines.size / 2) '0' else '1' }
        i++
    }
    val co2Rate = tempLines[0].toInt(2)
    println("CO2 scrubber rating: $co2Rate")

    println("o2($oxigenRate) * co2($co2Rate) = ${oxigenRate * co2Rate}")

}