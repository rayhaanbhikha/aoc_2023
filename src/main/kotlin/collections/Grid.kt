package collections

class Grid<K, V>(val values: MutableMap<K, V>) {
    constructor(): this(mutableMapOf<K, V>())

    fun set(key: K, value: V) {
        values[key] = value
    }

    fun get(key: K) = values[key]
}

fun <S, K, V>List<List<S>>.toGrid(mapper: (value: S, rowIndex: Int, colIndex: Int) -> Pair<K, V>): Grid<K, V> {
    val grid = Grid<K, V>()

    this.forEachIndexed { rowIndex, row ->
        row.forEachIndexed { colIndex, s ->
            val (key, value) = mapper(s, rowIndex, colIndex)
            grid.set(key, value)
        }
    }

    return grid
}