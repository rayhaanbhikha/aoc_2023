import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day05Test {

    @Test
    fun part1() {
        val input = InputLoader.loadAsString(5)
        val res = Day05(input).part1()
        assertEquals(261668924, res)
    }

    @Test
    fun part2() {
    }
}