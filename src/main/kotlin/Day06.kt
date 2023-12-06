import kotlin.math.*

class Day06 : Day {
    override fun part1() =
        listOf<Pair<Long, Long>>(
            Pair(53, 333),
            Pair(83, 1635),
            Pair(72, 1289),
            Pair(88, 1532),
        ).map { computeRace(it.first, it.second) }
            .fold(1.0) { acc, it -> it * acc }

    override fun part2() = computeRace(53837288L, 333163512891532L)
}

fun computeRace(time: Long, recordDistance: Long): Long {
    val lowerBound = computeTheSquare(-1, time, -recordDistance, 1)
    val upperBound = computeTheSquare(-1, time, -recordDistance, -1)

    val lRound = ceil(lowerBound)
    val uRound = floor(upperBound)

    val res = (uRound - lRound).toLong()
    return when {
        lRound - lowerBound == 0.0 && uRound - upperBound == 0.0 -> res - 1
        else -> res + 1
    }
}

fun computeTheSquare(a: Long, b: Long, c: Long, operator: Int): Double {
    return (-b + operator * sqrt(b.toDouble().pow(2) - 4 * a * c)).div(2 * a)
}

// hold * (time-hold) = X
// 2 * (7-2) = 10 > 9
// 3 * (7-3) = 12
// h * (7-h) > 9
// 7h - h^2 > 9
// 7h - h^2 = 10
// h^2 - 7h + 10 - (x = 2) * 2.

// 3 * (15-3) > 40
// h * (t - h) > R
// t*h - h^2 > R
// -h^2 + t*h - R > 0

// -2h + 15 = 0
// h = 15/2 = 7.5

// 11 * (30*11) = 209
// 12 * (30-12) = 216
// 13 * (30-13) = 221
// 14 * (30-14) = 224
// 15 * (30-15) = 225
// 16 * (30-16) = 224
// 17 * (30-17) = 221
// 18 * (30-18) = 216
// 19 * (30-19) = 209

// h * (t - h) > R
// t*h - h^2 = R
// -h^2 + t * h - R = 0

// -2h + t = 0
// -2h + 30
// h = 15
