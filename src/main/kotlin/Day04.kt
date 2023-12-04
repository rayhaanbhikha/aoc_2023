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
        val cardCopies = cards.associateBy(keySelector = { it.cardId}, valueTransform = { 1 }).toMutableMap()

        for (currentCard in cards) {
            val start = currentCard.cardId.plus(1)
            val end = currentCard.cardId.plus(currentCard.matchingNumbers)

            val copiesOfCurrentCard = cardCopies[currentCard.cardId]!!

            for (nextCardId in start..end) {
                val nextCard = cardsMap[nextCardId] ?: continue

                val nextCardCopies = cardCopies[nextCard.cardId]!! + copiesOfCurrentCard
                cardCopies[nextCard.cardId] = nextCardCopies
            }
        }

        return cardCopies.values.sum()
    }
}
