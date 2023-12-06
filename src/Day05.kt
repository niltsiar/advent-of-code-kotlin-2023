fun main() {
    fun part1(input: List<String>): Long {
        val seeds = parseSeeds(input.first())
        val ranges = parseMaps(input.drop(2))

        return seeds.minOf { seed ->
            ranges.fold(seed) { acc, maps ->
                maps.firstOrNull { range ->
                    range.source.contains(acc)
                }?.let { range ->
                    range.destination.first + (acc - range.source.first)
                } ?: acc
            }
        }
    }

    fun part2(input: List<String>): Long {
        // Brute force here was not the best option... it took a while
        val seedRanges = parseSeedRanges(input.first())
        val ranges = parseMaps(input.drop(2))

        return seedRanges
            .asSequence()
            .map { seedRange ->
                seedRange.asSequence()
            }
            .map {
                it.minOf { seed ->
                    ranges.fold(seed) { acc, maps ->
                        maps.firstOrNull { range ->
                            range.source.contains(acc)
                        }?.let { range ->
                            range.destination.first + (acc - range.source.first)
                        } ?: acc
                    }
                }
            }.toList()
            .min()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)
    check(part2(testInput) == 46L)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}

private fun parseSeeds(input: String): List<Long> {
    return input.substringAfter(":")
        .trim()
        .split(" ")
        .map { it.toLong() }
}

private fun parseSeedRanges(input: String): List<LongRange> {
    return input.substringAfter(":")
        .trim()
        .split(" ")
        .map { it.toLong() }
        .chunked(2) { (start, end) ->
            start..<(start + end)
        }
}

private fun parseMaps(input: List<String>): List<List<RangeMap>> {
    return input.joinToString("\n")
        .split("\n\n")
        .map { block ->
            block.split("\n")
                .drop(1)
                .map {
                    val (destination, source, length) = it.split(" ")
                        .map { it.toLong() }
                    RangeMap(
                        source = source..<(source + length),
                        destination = destination..<(destination + length)
                    )
                }
        }

}

private data class RangeMap(
    val source: LongRange,
    val destination: LongRange,
)
