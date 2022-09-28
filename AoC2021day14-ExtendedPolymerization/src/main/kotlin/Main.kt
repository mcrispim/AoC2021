import java.io.File

val transforms = mutableMapOf<Pair<String, Int>, Map<Char, Long>>()
var tableOfPairs = mapOf<String, String>()


fun main() {

    val input = File("data.txt").readLines()
    tableOfPairs = prepareTableOfPairs(input.slice(2..input.lastIndex))
    initialTransforms(tableOfPairs)

    val polymer = input[0]
    val steps = 40
    val elements = addMaps(mapOf(polymer[0] to 1), calculateElements(polymer, steps))
    val sortedElements = elements.values.sorted()
    val result = sortedElements.last() - sortedElements.first()

    println("After $steps steps, the quantity of the most common element minus the quantity of the least common element is $result")

}

fun calculateElements(polymer: String, steps: Int): Map<Char, Long> {
    //println("polymer: $polymer, steps: $steps")
    var map = mapOf<Char, Long>()
    if (transforms[Pair(polymer, steps)] != null) {
        map = transforms[Pair(polymer, steps)] as MutableMap<Char, Long>
    } else {
        if (steps == 0) {
            map = polymer.drop(1).toMap().mapValues { (k, v) -> v.toLong() }
        } else {
            for (i in 0 until polymer.lastIndex) {
                val part = polymer.slice(i..i + 1)
                val partElements = calculateElements(pairToTrio(part), steps - 1)
                map = addMaps(map, partElements)
            }
        }
        transforms[Pair(polymer, steps)] = map
    }
    return map
}

fun pairToTrio(part: String) = "" + part[0] + tableOfPairs[part] + part[1]

fun String.toMap() = this.groupingBy { it }.eachCount().mapValues { (k, v) -> v.toLong() }

fun initialTransforms(tableOfPairs: Map<String, String>) {
    for ((k,v) in tableOfPairs) {
        val all = k + v
        val map = all.toMap()
        transforms[Pair(k, 1)] = map
    }
}

fun prepareTableOfPairs(values: List<String>): Map<String, String> {
    val result = mutableMapOf<String, String>()
    for (v in values) {
        val (pair, output) = v.split(" -> ")
        result[pair] = output
    }
    return result.toMap()
}

fun addMaps(m1:Map<Char, Long>, m2: Map<Char, Long>): Map<Char, Long> {
    var result = m1.toMutableMap()
    for ((k, v) in m2) {
        result[k] = result.getOrDefault(k, 0) + v
    }
    return result.toMap()
}
