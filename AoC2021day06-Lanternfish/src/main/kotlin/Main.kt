import java.io.File

val fishes = mutableMapOf("0" to 0L, "1" to 0L, "2" to 0L, "3" to 0L, "4" to 0L, "5" to 0L, "6" to 0L, "7" to 0L, "8" to 0L)

fun processFishes() {
    val zeroFish = fishes["0"]!!
    fishes["0"] = fishes["1"]!!
    fishes["1"] = fishes["2"]!!
    fishes["2"] = fishes["3"]!!
    fishes["3"] = fishes["4"]!!
    fishes["4"] = fishes["5"]!!
    fishes["5"] = fishes["6"]!!
    fishes["6"] = fishes["7"]!! + zeroFish
    fishes["7"] = fishes["8"]!!
    fishes["8"] = zeroFish
}

fun main() {
    val input = File("data.txt")
    val inputFishes = input.readLines().first().split(",").groupingBy { it }.eachCount()
    for (fish in inputFishes.keys) {
        fishes[fish] = inputFishes[fish]?.toLong() ?: 0L
    }

    repeat(256) {
        processFishes()
    }
    println(fishes.values.sum())
}
