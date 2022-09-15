import java.io.File

val ways = mutableMapOf<String, MutableList<String>>()

fun main() {
    val input = File("data.txt").readLines()

    for (line in input) {
        val (start, end) = line.split("-")
        ways.getOrPut(start) { mutableListOf() }.add(end)
        ways.getOrPut(end) { mutableListOf() }.add(start)
    }

    val allPathways1 = searchPathways1()
    println("If you can only enter a little cave once, there are ${allPathways1.size} pathways from start to end.")

    val allPathways2 = searchPathways2()
    println("If you can enter one little cave twice, there are ${allPathways2.size} pathways from start to end.")
}

fun searchPathways1(): MutableList<MutableList<String>> {
    val validPathways = mutableListOf<MutableList<String>>()
    val pathsToSearch = mutableListOf(mutableListOf("start"))
    while (pathsToSearch.isNotEmpty()) {
        val myPath = pathsToSearch.removeAt(0)
        val myNode = myPath.last()
        if (myNode == "end") {
            validPathways.add(myPath)
            continue
        }
        for (node in ways[myNode]!!) {
            if (node.first().isLowerCase() && node in myPath) {
                continue
            }
            val tempPath = myPath.toMutableList()
            tempPath.add(node)
            pathsToSearch.add(tempPath)
        }
    }
    return validPathways

}

fun searchPathways2(): MutableList<MutableList<String>> {
    val validPathways = mutableListOf<MutableList<String>>()
    val pathsToSearch = mutableListOf<MutableList<String>>(mutableListOf("start"))
    while (pathsToSearch.isNotEmpty()) {
        val myPath = pathsToSearch.removeAt(0)
        val myNode = myPath.last()
        if (myNode == "end") {
            validPathways.add(myPath)
            continue
        }
        val lastNode = if (myNode.startsWith('_')) myNode.drop(1) else myNode
        for (node in ways[lastNode]!!) {
            var n = node
            if (node == "start") {
                continue
            }
            if (node.first().isLowerCase() && node in myPath) {
                if ("_$node" in myPath) {
                    continue
                } else {
                    n = "_$node"
                }
            }

            val tempPath = myPath.toMutableList()
            tempPath.add(n)
            if (!isValidPath(tempPath)) {
                continue
            }
            pathsToSearch.add(tempPath)
        }
    }
    return validPathways
}

fun isValidPath(path: List<String>) = path.filter { it.startsWith('_') }.count() <= 1

