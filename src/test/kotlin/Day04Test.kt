import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day04Test {

    @Test
    fun part1Example() {
        val input = InputLoader.loadExample(4)
        val res = Day04(input).part1()
        assertEquals(13, res)
    }

    @Test
    fun part1() {
        val input = InputLoader.load(4)
        val res = Day04(input).part1()
        assertEquals(23678, res)
    }

    @Test
    fun part2Example() {
        val input = InputLoader.loadExample(4)
        val res = Day04(input).part2()
        assertEquals(13, res)
    }

    @Test
    fun part2() {
        val input = InputLoader.load(4)
        val res = Day04(input).part2()
        assertEquals(13, res)
    }

}