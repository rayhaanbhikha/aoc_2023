package day1

import java.io.File

fun loadInput(): List<String> {
    return File("./src/main/resources/day1_input.txt").readLines().map { it.trim() }
}

fun part1(input: List<String>) {
    val res = input.mapNotNull { row ->
        val result = row.filter { c -> c.isDigit() }
        "${result.firstOrNull()}${result.lastOrNull()}".toIntOrNull()
    }.sum()

    println(res)
}

fun part2(input: List<String>) {
    val res = input.sumOf { row ->
        val r = parseAndSumRow(row)
//        println("$row -> $r")
        r
    }

    println(res)
}

data class Number(val word: String, val num: Int) {
    fun startsWith(otherWord: String) = word.startsWith(otherWord)
    fun matches(otherWord: String) = word == otherWord
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

data class Window(var start: Int, var end: Int) {
    fun increment() = end++
    fun startAtEnd() {
        start = end + 1
        end = start
    }

    fun move(step: Int = 1) {
        start += step
        end += step
    }

    val range: IntRange
        get() = IntRange(start, end)
}


fun parseAndSumRow(word: String): Int {
    val size = word.length - 1
    val numsFound = mutableListOf<Int>()
    val window = Window(0, 0)

    while (true) {
        if (window.start > size || window.end > size) {
            break
        }

        val w = word.substring(window.range)

        val lastChar = w.getOrNull(w.length - 1)

        val numberFound = numbers.find {
            it.startsWith(w)
        }

        when {
            lastChar?.digitToIntOrNull() != null -> {
                // matches straight number
                numsFound.add(lastChar.digitToInt())
                // reset window
                window.startAtEnd()
            }

            w.toIntOrNull() != null -> {
                // matches straight number
                numsFound.add(w.toInt())
                // reset window
                window.startAtEnd()
            }

            numberFound == null -> {
                // reset window.
                window.move()
            }

            numberFound.matches(w) -> {
                // matches completely
                numsFound.add(numberFound.num)
                // reset window
                window.startAtEnd()
            }

            else -> {
                window.increment()
            }
        }
    }


    println(numsFound)


    return when {
        numsFound.isEmpty() -> 0
        numsFound.size == 1 -> "${numsFound.first()}${numsFound.first()}".toInt()
        else -> "${numsFound.first()}${numsFound.last()}".toInt()
    }
}

fun main() {
    listOf(
        "4f4",
        "238xrtqfcfgsrmrhkxz6",
        "one2onefour",
        "123456789",
        "one2twthreethon4fivesixseveneightnine",
        "two8onepcddklbzfoureight8five",
        "4ontwoon1er2othree  ",
        "two1 nine",
        "eightwothree",
        "abcone2threexyz",
        "xtwone3four",
        "4nineeightseven2",
        "zoneight234",
        "7pqrstsixteen",
    ).onEach {
        println("$it -> ${parseAndSumRow(it)}")
    }
}