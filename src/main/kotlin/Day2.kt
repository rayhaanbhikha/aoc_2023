import java.io.File

data class Cube(val color: String, val count: Int)

data class Game(val game: String) {
    private val cubes: List<Cube>
    val id: Int

    init {
        val gameIdReg = Regex("Game (?<id>\\d+)")
        val reg = Regex("(?<count>\\d+) (?<color>red|blue|green)")

        val result = game.split(":")

        id = gameIdReg.find(result[0])?.groups?.get("id")?.value?.toInt() ?: 0

        cubes = result[1].split(";").map { turn ->
            reg.findAll(turn)
        }.map { it.toList() }.flatten().mapNotNull {
            val count = it.groups["count"]?.value?.toInt()
            val color = it.groups["color"]?.value
            if (count == null || color == null) return@mapNotNull null

            Cube(color, count)
        }
    }

    fun isPossible(): Boolean {
        return cubes.all {
            when (it.color) {
                "red" -> it.count <= 12
                "green" -> it.count <= 13
                "blue" -> it.count <= 14
                else -> true
            }
        }
    }

    fun computePower() = cubes.fold(mutableMapOf<String, Int>()) {
        acc, currentCube ->
        val currentCount = acc[currentCube.color]
        acc[currentCube.color] = when {
            currentCount == null || currentCount < currentCube.count -> currentCube.count
            else -> currentCount
        }
        acc
    }.map { it.value }.fold(1) { power, count -> power * count}
}

class Day2 {

    fun part1(input: List<String>): Int {
        val res = input.map {
            val game = Game(it)
            if (!game.isPossible()) return@map 0
            game.id
        }

        return res.sum()
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {
            Game(it).computePower()
        }
    }

    companion object {
        fun loadInput(): List<String> {
            return File("./src/main/resources/day2_input.txt").readLines().map { it.trim() }
        }
    }
}

fun main() {
    val input = listOf(
        "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
        "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
        "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
        "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
        "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green",
    )
    println(Day2().part2(input))
}