fun main() {
    fun part1(input: List<String>): Int {
        return input.mapNotNull { string -> string.parseIdsOfPossibleGames() }
            .sum()
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { string -> string.getPowerSetOfCubes() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

private val maxColors = mapOf(
    "red" to 12,
    "green" to 13,
    "blue" to 14,
)

private fun String.parseIdsOfPossibleGames(): Int? {
    val id = substringBefore(":")
        .substringAfter(" ")
        .toInt()
    val cubes = getCubes()

    val isImpossibleGame = cubes.any { (color, number) ->
        number > maxColors.getOrDefault(color, 0)
    }

    return if (isImpossibleGame) null else id
}

private fun String.getPowerSetOfCubes(): Int {
    val cubes = getCubes()
    val maxCubes = cubes.groupBy { it.first }
        .mapValues { (_, values) -> values.maxOf { it.second } }

    return maxCubes.values.fold(1) { acc, i -> acc * i }
}

private fun String.getCubes(): List<Pair<String, Int>> {
    return substringAfter(": ")
        .split("; ")
        .map { round -> round.split(", ") }
        .flatten()
        .map { cube ->
            val (number, color) = cube.split(" ")
            color to number.toInt()
        }
}


