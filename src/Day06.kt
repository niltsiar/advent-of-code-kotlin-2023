fun main() {
    fun part1(input: List<String>): Int {
        val times = input.first()
            .substringAfter(":")
            .split(" ")
            .filter { it.isNotBlank() }
            .map { it.toInt() }
        val distances = input.drop(1).first()
            .substringAfter(":")
            .split(" ")
            .filter { it.isNotBlank() }
            .map { it.toInt() }

        return times.zip(distances).map {
            val (time, distance) = it
            (1..<time).count { hold ->
                val testDistance = (time - hold) * hold
                testDistance > distance
            }
        }.fold(1) { acc, i -> acc * i }
    }

    fun part2(input: List<String>): Int {
        val time = input.first()
            .substringAfter(":")
            .filter { it != ' ' }
            .toLong()
        val distance = input.drop(1).first()
            .substringAfter(":")
            .filter { it != ' ' }
            .toLong()

        return (1..<time).count { hold ->
            val testDistance = (time - hold) * hold
            testDistance > distance
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288)
    check(part2(testInput) == 71503)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
