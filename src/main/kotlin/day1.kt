import java.io.File

fun loadInput(): List<String> {
    return File("./src/main/resources/day1_input.txt").readLines().map { it.trim() }
}

fun part1(input: List<String>): Int {
    return input.mapNotNull { row ->
        val result = row.filter { c -> c.isDigit() }
        "${result.firstOrNull()}${result.lastOrNull()}".toIntOrNull()
    }.sum()
}

fun part2(input: List<String>): Int {
    return input.sumOf { it.parseAndSumRow()}
}

enum class NumberCheck {
    MATCHES,
    STARTS_WITH,
    FAILED
}

data class Number(val word: String, val num: Int) {
    fun check(otherWord: String) = when {
        word == otherWord -> NumberCheck.MATCHES
        word.startsWith(otherWord) -> NumberCheck.STARTS_WITH
        else -> NumberCheck.FAILED
    }
}

val numbers = listOf(
    Number("one", 1),
    Number("two", 2),
    Number("three", 3),
    Number("four", 4),
    Number("five", 5),
    Number("six", 6),
    Number("seven", 7),
    Number("eight", 8),
    Number("nine", 9),
)


private fun String.parseAndSumRow(): Int {
    val wordIter = this.iterator()
    var currentWord = ""
    val numbersFound = mutableListOf<Int>()

    while (wordIter.hasNext()) {
        val char = wordIter.nextChar()

        when {
            char.isDigit() -> {
                // character is a number.
                numbersFound.add(char.digitToInt())
                // reset current word.
                currentWord = ""
            }

            else -> {
                // add letter to current word.
                currentWord += char

                // check if word matches.
                val numberMatched = numbers.find {
                    it.check(currentWord) != NumberCheck.FAILED
                }

                when {
                    numberMatched == null -> {
                        currentWord = currentWord.substring(1)
                    }

                    numberMatched.check(currentWord) == NumberCheck.MATCHES -> {
                        numbersFound.add(numberMatched.num)
                        currentWord = currentWord.substring(currentWord.length-1)
                    }

                    numberMatched.check(currentWord) == NumberCheck.STARTS_WITH -> {
                        continue
                    }
                }
            }
        }
    }

    return when {
        numbersFound.isEmpty() -> 0
        numbersFound.size == 1 -> "${numbersFound.first()}${numbersFound.first()}".toInt()
        else -> "${numbersFound.first()}${numbersFound.last()}".toInt()
    }
}