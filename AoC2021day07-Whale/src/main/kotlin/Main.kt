import java.io.File
import java.lang.Math.abs

fun calculateIndividualFuel(dist: Int): Int {
    var fuel = 0
    for (d in 0..dist) {
        fuel += d
    }
    return fuel
}

fun calculateFuel(crabs: Map<Int, Int>, goal: Int): Int {
    var fuel = 0
    for (x in crabs.keys) {
        fuel += crabs[x]!! * calculateIndividualFuel(abs(x - goal))
    }
    return fuel
}

fun main() {
    val input = File("data.txt")
    val inputCrabs = input.readLines().first().split(",").map { it.toInt() }.groupingBy { it }.eachCount()
    val locations = inputCrabs.keys.sorted()

    var minFuel = Int.MAX_VALUE
    var minFuelX = -1
    var maxFuel = Int.MIN_VALUE
    var maxFuelX = -1
    for (x in locations.first()..locations.last()) {
        val fuel = calculateFuel(inputCrabs, x)
        if (fuel < minFuel) {
            minFuel = fuel
            minFuelX = x
        }
        if (fuel > maxFuel) {
            maxFuel = fuel
            maxFuelX = x
        }
    }
    println(" a melhor posição é $minFuelX, que vai demandar $minFuel fuel.")
}