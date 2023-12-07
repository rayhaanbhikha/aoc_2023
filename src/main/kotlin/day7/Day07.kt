package day7

import Day
import InputLoader
import java.lang.Exception

enum class HandType(rank: Int) {
    HIGH_CARD(0), // ABCDE
    ONE_PAIR(1), // AABCD
    TWO_PAIR(2), // AABBC
    THREE_OF_A_KIND(3), //
    FULL_HOUSE(4),
    FOUR_OF_A_KIND(5),
    FIVE_OF_A_KIND(6)
}

fun HandType.upgrade() {

}

data class Card(val value: Char) {
    val strength = when (value) {
        'A' -> 14
        'K' -> 13
        'Q' -> 12
        'J' -> 11
        'T' -> 10
        else -> value.digitToIntOrNull()
    } ?: 0
}

class Hand(val inputRow: String) : Comparable<Hand> {
    val cards: Set<Card>
    val handType: HandType
//    val upgradedHandType: HandType
    val bid: Int

    init {
        val result = inputRow.split(" ")
        cards = result[0].map { Card(it) }.toSet()
        bid = result[1].toIntOrNull() ?: throw Exception("failed to parse bid: $result")
        val cardsMap = cards.groupBy { it.value }
        handType = when (cardsMap.size) {
            5 -> HandType.HIGH_CARD
            4 -> HandType.ONE_PAIR
            3 -> if (cardsMap.any { entry -> entry.value.size == 3 }) HandType.THREE_OF_A_KIND else HandType.TWO_PAIR
            2 -> if (cardsMap.any {entry -> entry.value.size == 3 }) HandType.FULL_HOUSE else HandType.FOUR_OF_A_KIND
            1 -> HandType.FIVE_OF_A_KIND
            else -> throw Exception("card size cannot be any thing else")
        }
    }

    override fun compareTo(other: Hand): Int = when {
        handType > other.handType -> 1
        handType < other.handType -> -1
        else -> {
            cards.zip(other.cards).map {
                when {
                    it.first.strength > it.second.strength -> 1
                    it.first.strength < it.second.strength -> -1
                    else -> 0
                }
            }.first { it != 0 }
        }
    }
}


class Day07(private val input: List<String>) : Day {
    override fun part1() = input
        .map { Hand(it) }
        .sorted()
        .onEach {
            println("${it.inputRow}, ${it.bid}, ${it.handType}")
        }
        .foldIndexed(0L) { index, acc, hand -> (index+1)*hand.bid + acc }

    override fun part2(): Any? {
        TODO("Not yet implemented")
    }
}

fun main() {
    val input = InputLoader.load(7)
    Day07(input).part1()
}