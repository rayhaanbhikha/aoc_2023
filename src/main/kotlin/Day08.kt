import kotlin.Number

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
    override fun part1(): Int {
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

        return steps
    }

    private fun walkFromNode(graph: Map<String, Pair<String, String>>, startingNode: String): Int {
        var instructions = input[0].iterator()
        var currentNode = startingNode
        var steps = 0

        while (!currentNode.endsWith('Z')) {
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

        return steps
    }

    override fun part2(): Long {
        val graph = constructGraph(input.drop(2))

        return graph
            .keys
            .filter { it.endsWith('A') }
            .map { currentNode -> walkFromNode(graph, currentNode).toLong() }
            .reduce { acc, i ->
                acc * i / gcd(acc, i)
            }
    }
}

fun gcd(num1: Long, num2: Long): Long {
    var n1 = num1
    var n2 = num2
    while (n1 != n2) {
        if (n1 > n2)
            n1 -= n2
        else
            n2 -= n1
    }
    return n1
}