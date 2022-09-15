import java.io.File

fun main() {
    val input = File("sonardata.txt")
    val depths = input.readLines().map { it.toInt() }
    println("Number of depth points read: ${depths.size}")

    var increased = 0
    var lastDepth = depths[0]
    for (i in 1..depths.lastIndex) {
        if (depths[i] > lastDepth) {
            increased++
        }
        lastDepth = depths[i]
    }
    println("The number of times the depth incresed is $increased.")

    increased = 0
    lastDepth = depths[0] + depths[1] + depths[2]
    for (i in 1..depths.lastIndex - 2) {
        val tripleDepth = depths[i] + depths[i + 1] + depths[i + 2]
        if (tripleDepth > lastDepth) {
            increased++
        }
        lastDepth = tripleDepth
    }
    println("The number of times the triple depths incresed is $increased.")

}