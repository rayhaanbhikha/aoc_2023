import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day01Test {

    @Test
    fun part1() {
        val input = InputLoader.load(1)
        val res = Day01(input).part1()
        assertEquals(55607, res)
    }

    @Test
    fun part2() {
        val input = InputLoader.load(1)
        val res = Day01(input).part2()
        assertEquals(55291, res)
    }
}