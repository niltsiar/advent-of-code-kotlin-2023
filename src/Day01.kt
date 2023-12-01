fun main() {
    fun part1(input: List<String>): Int {
        return input.findFirstAndLastDigitAndSum()
    }

    fun part2(input: List<String>): Int {
        return input.map { it.extractDigits() }
            .findFirstAndLastDigitAndSum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)
    val testInputB = readInput("Day01b_test")
    check(part2(testInputB) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}


private fun List<String>.findFirstAndLastDigitAndSum(): Int {
    return sumOf { string ->
        val digits = string.filter { char -> char.isDigit() }

        val firstDigit = digits.first()
        val lastDigit = digits.last()

        "$firstDigit$lastDigit".toInt()
    }
}

private val numberStringToDigit = mapOf(
    "one" to "1",
    "two" to "2",
    "three" to "3",
    "four" to "4",
    "five" to "5",
    "six" to "6",
    "seven" to "7",
    "eight" to "8",
    "nine" to "9",
)

private fun String.extractDigits(): String {
    val line = this
    return buildString {
        for (i in line.indices) {
            if (line[i].isDigit()) {
                append(line[i])
            } else {
                val sub = line.substring(startIndex = i)
                for ((word, num) in numberStringToDigit) {
                    if (sub.startsWith(word)) {
                        append(num)
                        break
                    }
                }
            }
        }
    }
}
