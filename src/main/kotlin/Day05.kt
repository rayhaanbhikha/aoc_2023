import kotlin.math.abs

fun main() {
    val input = InputLoader.loadExampleAsString(5)
    println(Day05(input).part1())
}

data class Range(val destinationStart: Long, val sourceStart: Long, val rangeLength: Long) {
    private val sourceStartRange = LongRange(sourceStart, sourceStart + rangeLength)
    fun getNumber(startNum: Long): Long {
        if (!sourceStartRange.contains(startNum)) return startNum
        return abs(startNum.minus(sourceStartRange.first)) + destinationStart
    }
}

fun List<Range>.getNumber(startNum: Long): Long {
    return this.map { range -> range.getNumber(startNum) }.firstOrNull { mappedNum -> mappedNum != startNum }
        ?: startNum
}

class Day05(val input: String) : Day {

    private fun parseNumbers(row: String) = row.split(" ").mapNotNull { it.toLongOrNull() }

    override fun part1(): Long? {
        val seeds = parseNumbers(input.substringBefore("\n"))
        val res = input.trim().split("\n\n")
            .drop(1)
            .map { row ->
                row.lines()
                    .drop(1)
                    .map{ numbs -> parseNumbers(numbs) }
                    .map { Range(it[0], it[1], it[2]) }
            }

        return seeds.minOfOrNull{ seed ->
            res.fold(seed){ currentSeed, ranges ->
                ranges.getNumber(currentSeed)
            }
        }
    }

    override fun part2(): Any {
        println(input)
        return 0
    }
}