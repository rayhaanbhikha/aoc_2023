import kotlin.math.abs

class Day09(private val input: List<String>) : Day {
    override fun part1() = input
        .sumOf {
            val numbers = it.trim().split(" ").mapNotNull { num -> num.toIntOrNull() }
            computeNextNum(numbers)
        }
        .also { println(it) }


    override fun part2() = input
        .sumOf {
            val numbers = it.trim().split(" ").mapNotNull { num -> num.toIntOrNull() }
            computePrevNum(numbers)
        }
        .also { println(it) }

    private fun computePrevNum(numbers: List<Int>): Int {
        val lastNum = mutableListOf(numbers.first())
        var currentNumbers = numbers
        while (!currentNumbers.all { it == 0 }) {
            currentNumbers = currentNumbers.foldIndexed(mutableListOf()) { index, acc, currentNum ->
                if (index != 0) acc.add(currentNum - currentNumbers[index - 1])
                acc
            }
            if (currentNumbers.isNotEmpty()) {
                lastNum.add(currentNumbers.first())
            }
        }

        return lastNum.reversed().reduce { acc, i ->
            i - acc
        }
    }

    private fun computeNextNum(numbers: List<Int>): Int {
        val lastNum = mutableListOf(numbers.last())
        var currentNumbers = numbers
        while (!currentNumbers.all { it == 0 }) {
            currentNumbers = currentNumbers.foldIndexed(mutableListOf()) { index, acc, currentNum ->
                if (index != 0) acc.add(currentNum - currentNumbers[index - 1])
                acc
            }
            if (currentNumbers.isNotEmpty()) {
                lastNum.add(currentNumbers.last())
            }
        }

        return lastNum.reduce { acc, i -> acc + i }
    }


}

fun main() {
    val input = InputLoader.load(9)
    Day09(input).part2()
}