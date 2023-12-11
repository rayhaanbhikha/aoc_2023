import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day08Test {

    @Test
    fun part1() {
        val input = InputLoader.load(8)
        assertEquals(17263, Day08(input).part1())
    }

    @Test
    fun part2() {
        val input = InputLoader.load(8)
        assertEquals(14631604759649, Day08(input).part2())
    }
}