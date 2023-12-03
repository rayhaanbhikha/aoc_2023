import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day02Test {

    @Test
    fun part1() {
        val input = InputLoader.load(2)
        val res = Day02(input).part1()
        assertEquals(2156, res)
    }

    @Test
    fun part2() {
        val input = InputLoader.load(2)
        val res = Day02(input).part2()
        assertEquals(66909, res)
    }
}