import java.io.File

fun main() {
    val input = File("data1.txt").readLines()

    val paths = mutableMapOf<String, MutableList<String>>()
    for (line in input) {
        val (start, end) = line.split("-")
        paths.getOrPut(start) { mutableListOf<String>() }.add(end)
        paths.getOrPut(end) { mutableListOf<String>() }.add(start)
        println("$start    $end")
    }
    println(paths)
    var node = "start"
    val pathways = mutableListOf<List<String>>()
    var pathway = mutableListOf(node)
    go(node, pathway, paths, pathways)
    println("there is ${pathways.size} pathways from start to end.")
}

fun go(node: String, pathway: MutableList<String>, paths: MutableMap<String, MutableList<String>>, pathways: MutableList<List<String>>) {
    for (n in paths[node]!!) {
        if (n == "end") {
            pathways.add(pathway)
            return
        }
        if (n.first().isUpperCase() || n !in pathway) {
            pathway.add(n)
            go(n, pathway, paths, pathways)
        }
    }
}
