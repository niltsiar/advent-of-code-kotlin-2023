fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            val numbers = it.split(" ").map(String::toInt)
            numbers.nextNumber()
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {
            val numbers = it.split(" ").map(String::toInt)
            numbers.previousNumber()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 114)
    check(part2(testInput) == 2)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}

fun List<Int>.calculateDifferences(): List<Int> {
    return windowed(2, 1) { (a, b) -> b - a }
}

fun List<Int>.nextNumber(): Int {
    val differences = calculateDifferences()
    if (differences.all { it == 0 }) {
        return last()
    }
    return last() + differences.nextNumber()
}

fun List<Int>.previousNumber(): Int {
    val differences = calculateDifferences()
    if (differences.all { it == 0 }) {
        return first()
    }
    return first() - differences.previousNumber()
}
