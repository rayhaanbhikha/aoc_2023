import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day03Test {

    @Test
    fun part1Example() {
        val input = InputLoader.loadExample(3)
        val res = Day03(input).part1()
        assertEquals(4361, res)
    }

    @Test
    fun part1() {
        val input = InputLoader.load(3)
        val res = Day03(input).part1()
        assertEquals(539433, res)
    }

    @Test
    fun part2Example() {
        val input = InputLoader.loadExample(3)
        val res = Day03(input).part2()
        assertEquals(467835, res)
    }

    @Test
    fun part2() {
        val input = InputLoader.load(3)
        val res = Day03(input).part2()
        assertEquals(75847567, res)
    }
}