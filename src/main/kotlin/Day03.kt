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

data class Coord(val row: Int, val col: Int) {
    fun getAdjacentCoords(): List<Coord> {
        return listOf(
            Coord(row - 1, col - 1), // NW
            Coord(row - 1, col), // N
            Coord(row - 1, col + 1), // NE
            Coord(row, col - 1), // E
            Coord(row + 1, col + 1), // SE
            Coord(row + 1, col), // S
            Coord(row + 1, col - 1), // SW
            Coord(row, col + 1), // W
        )
    }

    fun east(): Coord = Coord(row, col + 1)
    fun west(): Coord = Coord(row, col - 1)
}


data class Cell(val coord: Coord, val value: Char) {
    fun isPartNumber() = value.isDigit()
    fun isSymbol() = value != '.' && !isPartNumber()
}

class Engine(raw: List<List<Char>>) {
    val grid = mutableMapOf<Coord, Cell>()

    init {
        raw.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, s ->
                val coord = Coord(rowIndex, colIndex)
                grid[coord] = Cell(coord, value = s)
            }
        }
    }

    fun sumPartNumbers() = grid.mapNotNull { (_, cell) ->
        if (!cell.isSymbol()) 0 else
            getPartNumbers(cell).sum()
    }.sum()


    fun sumPartNumbersWithRatio(): Int {
        return grid.mapNotNull { (_, cell) ->
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
    }

    private fun getPartNumbers(cell: Cell) = cell.coord.getAdjacentCoords().mapNotNull {
        grid[it]
    }.filter {
        it.isPartNumber()
    }.fold(mutableSetOf<Int>()) { numbersFound, it ->
        val leftChars = mutableListOf<Char>()
        val rightChars = mutableListOf<Char>()

        // go right
        var rightCoord = it.coord.east()
        while (true) {
            val nextCell = grid[rightCoord] ?: break
            if (!nextCell.isPartNumber()) break
            rightChars.add(nextCell.value)
            rightCoord = rightCoord.east()
        }

        // go left
        var leftCoord = it.coord.west()
        while (true) {
            val nextCell = grid[leftCoord] ?: break
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