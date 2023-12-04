import kotlin.math.pow

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { card ->
            val winningNumbers = card.substringAfter(":")
                .trim()
                .substringBefore("|")
                .trim()
                .split(" ")
                .filter { it.isNotEmpty() }
                .map { number -> number.trim().toInt() }
                .toSet()
            val cardNumbers = card.substringAfter("|")
                .trim()
                .split(" ")
                .filter { it.isNotEmpty() }
                .map { number -> number.trim().toInt() }
                .toSet()
            val matches = winningNumbers.intersect(cardNumbers)

            when (val matchesSize = matches.size) {
                0 -> 0
                1 -> 1
                else -> 2.0.pow(matchesSize - 1).toInt()
            }
        }
    }

    fun part2(input: List<String>): Int {
        val scratchCards = MutableList(input.size + 1) { 0 }
        input.forEach { card ->
            val id = card.substringAfter(" ")
                .substringBefore(":")
                .trim()
                .toInt()
            scratchCards[id] = scratchCards[id] + 1
            val winningNumbers = card.substringAfter(":")
                .trim()
                .substringBefore("|")
                .trim()
                .split(" ")
                .filter { it.isNotEmpty() }
                .map { number -> number.trim().toInt() }
                .toSet()
            val cardNumbers = card.substringAfter("|")
                .trim()
                .split(" ")
                .filter { it.isNotEmpty() }
                .map { number -> number.trim().toInt() }
                .toSet()
            val matches = winningNumbers.intersect(cardNumbers)
            for (i in (id + 1)..(id + matches.size)) {
                scratchCards[i] = scratchCards[i] + scratchCards[id]
            }
        }

        return scratchCards.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
