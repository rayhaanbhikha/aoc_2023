package collections
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