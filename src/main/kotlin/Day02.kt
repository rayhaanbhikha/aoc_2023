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

class Day02(private val input: List<String>): Day {

    override fun part1(): Int {
        val res = input.map {
            val game = Game(it)
            if (!game.isPossible()) return@map 0
            game.id
        }

        return res.sum()
    }

    override fun part2(): Int {
        return input.sumOf {
            Game(it).computePower()
        }
    }
}