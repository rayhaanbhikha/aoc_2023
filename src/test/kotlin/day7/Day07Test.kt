package day7

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day07Test {

    @Test
    fun part1() {
        val input = InputLoader.load(7)
        assertEquals(253638586, Day07(input).part1())
    }

    @Test
    fun part2() {
    }
}