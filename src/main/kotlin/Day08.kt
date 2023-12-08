fun constructGraph(input: List<String>): Map<String, Pair<String, String>> {
    val reg = Regex("(?<nodeid>\\w+) = \\((?<left>\\w{0,3}+), (?<right>\\w+)\\)")
    return input.mapNotNull {
        val str = it.trim()
        val matchResult = reg.find(str) ?: return@mapNotNull null
        val nodeId = matchResult.groups["nodeid"]?.value
        val left = matchResult.groups["left"]?.value
        val right = matchResult.groups["right"]?.value
        if (nodeId == null || left == null || right == null) return@mapNotNull null
        Pair(nodeId, Pair(left, right))
    }.associateBy(
        keySelector = { it.first },
        valueTransform = { it.second }
    )
}


class Day08(private val input: List<String>) : Day {
    override fun part1(): Any? {


        val graph = constructGraph(input.drop(2))

        var instructions = input[0].iterator()
        var currentNode = "AAA"
        var steps = 0

        while (currentNode != "ZZZ") {
            if (!instructions.hasNext()) {
                instructions = input[0].iterator()
            }
            val nextInstruction = instructions.next()
            val nextNodes = graph[currentNode]!!
            currentNode = when (nextInstruction) {
                'R' -> nextNodes.second
                else -> nextNodes.first
            }
            steps++
        }

        println(steps)
        return 0
    }

    override fun part2(): Any? {
        TODO("Not yet implemented")
    }
}

fun main() {
    Day08("""
        RL

        AAA = (BBB, CCC)
        BBB = (DDD, EEE)
        CCC = (ZZZ, GGG)
        DDD = (DDD, DDD)
        EEE = (EEE, EEE)
        GGG = (GGG, GGG)
        ZZZ = (ZZZ, ZZZ)
    """.trimIndent().lines()).part1()

    Day08("""
LLR

AAA = (BBB, BBB)
BBB = (AAA, ZZZ)
ZZZ = (ZZZ, ZZZ)
    """.trimIndent().lines()).part1()

    val input = InputLoader.load(8)
    Day08(input).part1()
}