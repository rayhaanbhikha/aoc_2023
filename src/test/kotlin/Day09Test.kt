import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day09Test {

    @Test
    fun part1() {
        val input = InputLoader.load(9)
        assertEquals(2038472161, Day09(input).part1())
    }

    @Test
    fun part2() {
        val input = InputLoader.load(9)
        assertEquals(1091, Day09(input).part2())
    }
}