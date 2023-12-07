package day7

import Day
import InputLoader
import java.lang.Exception

enum class HandType(val rank: Int) {
    HIGH_CARD(0),
    ONE_PAIR(1),
    TWO_PAIR(2),
    THREE_OF_A_KIND(3),
    FULL_HOUSE(4),
    FOUR_OF_A_KIND(5),
    FIVE_OF_A_KIND(6);

    companion object {
        private fun from(newRank: Int): HandType {
            val nr = when {
                newRank > 6 -> 6
                newRank < 0 -> 0
                else -> newRank
            }
            return entries.firstOrNull { it.rank == nr }!!
        }

        fun computeHand(cardsMap: Map<Char, Int>) = when (cardsMap.size) {
            5 -> HIGH_CARD
            4 -> ONE_PAIR
            3 -> if (cardsMap.any { entry -> entry.value == 3 }) THREE_OF_A_KIND else TWO_PAIR
            2 -> if (cardsMap.any { entry -> entry.value == 3 }) FULL_HOUSE else FOUR_OF_A_KIND
            1 -> FIVE_OF_A_KIND
            else -> throw Exception("card size cannot be any thing else")
        }
    }
}


data class Card(val value: Char, val isPart2: Boolean = false) {
    val strength = when (value) {
        'A' -> 14
        'K' -> 13
        'Q' -> 12
        'J' -> if (isPart2) 1 else 11
        'T' -> 10
        else -> value.digitToIntOrNull()
    } ?: 0
}

class Hand(val inputRow: String, val shouldUpgrade: Boolean = false) : Comparable<Hand> {
    val cards: List<Card>
    val cardsMap: Map<Char, Int>
    var handType: HandType
    val bid: Int

    init {
        val result = inputRow.split(" ")
        cards = result[0].map { Card(it, shouldUpgrade) }.toList()
        bid = result[1].toIntOrNull() ?: throw Exception("failed to parse bid: $result")
        cardsMap = cards.fold(mutableMapOf()) { acc, card ->
            acc[card.value] = (acc[card.value] ?: 0) + 1
            acc
        }
        handType = HandType.computeHand(cardsMap)
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

    fun upgradeHand() {
        val numberOfJs = cardsMap['J'] ?: 0
        var maxCharKey: Char? = null
        var maxCharVal = 0
        for ((char, count) in cardsMap) {
            if (char == 'J') continue
            if (count > maxCharVal) {
                maxCharKey = char
                maxCharVal = count
            }
        }
        if(maxCharKey == null) return
        val m = cardsMap.toMutableMap()
        m.remove('J')
        m[maxCharKey] = maxCharVal + numberOfJs
        handType = HandType.computeHand(m)
    }
}


class Day07(private val input: List<String>) : Day {
    override fun part1() = input
        .map { Hand(it) }
        .sorted()
        .foldIndexed(0L) { index, acc, hand -> (index + 1) * hand.bid + acc }

    override fun part2() = input
        .map {
            val h = Hand(it, true)
            h.upgradeHand()
            h
        }
        .sorted()
        .foldIndexed(0L) { index, acc, hand -> (index + 1) * hand.bid + acc }
}
