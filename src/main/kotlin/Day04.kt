import java.lang.Exception
import kotlin.math.pow


class Card(rawInput: String) {
    var cardId: Int
    private var winningNumbers: Set<Int>
    private var numbers: Set<Int>

    init {
        val reg = Regex("Card\\ +(?<cardid>\\d{0,}.+): (?<winningnumbers>\\d{0,2}.+) \\| (?<numbers>\\d{0,2}.+)")
        val groups = reg.find(rawInput)?.groups
        cardId = groups?.get("cardid")?.value?.toIntOrNull() ?: 0
        numbers = groups?.get("numbers")?.value?.split(" ")?.mapNotNull { it.trim().toIntOrNull() }?.toSet() ?: setOf()
        winningNumbers = groups?.get("winningnumbers")?.value?.split(" ")?.mapNotNull { it.trim().toIntOrNull() }?.toSet() ?: setOf()
    }

    val matchingNumbers = numbers.intersect(winningNumbers).size

    val score = 2.0.pow(numbers.intersect(winningNumbers).size-1).toInt()
}

class Day04(private val input: List<String>): Day {
    override fun part1() = input.sumOf { Card(it).score }

    override fun part2(): Int {
        val cards = input.map { Card(it) }
        val cardsMap = cards.associateBy { it.cardId }
        val cardStack = cards.toMutableList()
        val cardSeen = mutableMapOf<Int, Int>()

        while(cardStack.isNotEmpty()) {
            val currentCard = cardStack.removeFirst()

            cardSeen[currentCard.cardId] = cardSeen[currentCard.cardId]?.plus(1) ?: 1

            val start = currentCard.cardId.plus(1)
            val end = currentCard.cardId.plus(currentCard.matchingNumbers)

            for (nextCardId in start..end) {
                val nextCard = cardsMap[nextCardId] ?: continue
                cardStack.add(nextCard)
            }

            println(cardStack.size)
        }

        return cardSeen.values.sum()
    }
}

fun main() {
//    Card("Card   1: 34 50 18 44 19 35 47 62 65 26 | 63  6 27 15 60  9 98  3 61 89 31 43 80 37 54 49 92 55  8  7 10 16 52 33 45")
    val input = InputLoader.load(4)
    println(Day04(input).part2())
}