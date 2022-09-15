import java.io.File

fun main() {
    val input = File("instructions.txt")
    val instructions = input.readLines().map { text ->
        val parts = text.split(" ")
        Pair(parts[0], parts[1].toInt())
    }

    var local = 0
    var depth = 0
    for (instruction in instructions) {
        when(instruction.first) {
            "forward" -> local += instruction.second
            "down" -> depth += instruction.second
            "up" -> depth -= instruction.second
        }
    }
    println("Local: $local    Depth: $depth    Result (local * depth): ${local * depth}")

    local = 0
    depth = 0
    var aim = 0
    for (instruction in instructions) {
        when(instruction.first) {
            "forward" -> {
                local += instruction.second
                depth += (instruction.second * aim)
            }
            "down" -> aim += instruction.second
            "up" -> aim -= instruction.second
        }
    }
    println("Correct result: Local: $local    Depth: $depth    Result (local * depth): ${local * depth}")
}