import collections.Coord
import collections.Grid
import collections.toGrid

class Day03(private val input: List<String>) : Day {
    override fun part1(): Int {
        val parsedInput = input.map { it.toCharArray().toList() }
        val engine = Engine(parsedInput)
        return engine.sumPartNumbers()
    }

    override fun part2(): Int {
        val parsedInput = input.map { it.toCharArray().toList() }
        val engine = Engine(parsedInput)
        return engine.sumPartNumbersWithRatio()
    }
}


data class Cell(val coord: Coord, val value: Char) {
    fun isPartNumber() = value.isDigit()
    fun isSymbol() = value != '.' && !isPartNumber()
}

class Engine(raw: List<List<Char>>) {
    private val grid = raw.toGrid{ value, rowIndex, colIndex ->
        val coord = Coord(rowIndex, colIndex)
        Pair(coord, Cell(coord, value) )
    }

    fun sumPartNumbers() = grid.values.mapNotNull { (_, cell) ->
        if (!cell.isSymbol()) 0 else
            getPartNumbers(cell).sum()
    }.sum()


    fun sumPartNumbersWithRatio(): Int = grid.values.mapNotNull { (_, cell) ->
        if (!cell.isSymbol()) {
            return@mapNotNull null
        }

        val numbers = getPartNumbers(cell)

        when {
            cell.value == '*' && numbers.size == 2 -> {
                numbers.multiply()
            }

            else -> null
        }

    }.sum()

    private fun getPartNumbers(cell: Cell) = cell.coord.getAdjacentCoords().mapNotNull {
        grid.get(it)
    }.filter {
        it.isPartNumber()
    }.fold(mutableSetOf<Int>()) { numbersFound, it ->
        val leftChars = mutableListOf<Char>()
        val rightChars = mutableListOf<Char>()

        // go right
        var rightCoord = it.coord.east()
        while (true) {
            val nextCell = grid.get(rightCoord) ?: break
            if (!nextCell.isPartNumber()) break
            rightChars.add(nextCell.value)
            rightCoord = rightCoord.east()
        }

        // go left
        var leftCoord = it.coord.west()
        while (true) {
            val nextCell = grid.get(leftCoord) ?: break
            if (!nextCell.isPartNumber()) break
            leftChars.add(nextCell.value)
            leftCoord = leftCoord.west()
        }
        leftChars.reverse()


        val number = "${leftChars.joinToString("")}${it.value}${rightChars.joinToString("")}".toIntOrNull()
            ?: return@fold numbersFound

        numbersFound.add(number)

        numbersFound
    }.toList()
}

internal fun List<Int>.multiply(): Int = this.fold(1) { acc, num -> acc * num }