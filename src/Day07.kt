fun main() {
    fun part1(input: List<String>): Int {
        return input.map {
            val (cards, bid) = it.split(" ")
            Hand(cards, bid.toInt(), false)
        }
            .sorted()
            .foldIndexed(0) { index, acc, hand ->
                acc + hand.bid * (index + 1)
            }
    }

    fun part2(input: List<String>): Int {
        return input.map {
            val (cards, bid) = it.split(" ")
            Hand(cards, bid.toInt(), true)
        }
            .sorted()
            .foldIndexed(0) { index, acc, hand ->
                acc + hand.bid * (index + 1)
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440)
    check(part2(testInput) == 5905)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}

private val cardStrength = listOf('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A')
private val cardStrengthWithJoker = listOf('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A')

data class Hand(
    val cards: String,
    val bid: Int,
    val withJoker: Boolean,
) : Comparable<Hand> {

    override fun compareTo(other: Hand): Int {
        return if (type != other.type) {
            HAND_TYPE.indexOf(type) - HAND_TYPE.indexOf(other.type)
        } else {
            val currentCardStrength = if (withJoker) {
                cardStrengthWithJoker
            } else {
                cardStrength
            }
            cards.forEachIndexed { index, c ->
                if (currentCardStrength.indexOf(c) != currentCardStrength.indexOf(other.cards[index])) {
                    return currentCardStrength.indexOf(c) - currentCardStrength.indexOf(other.cards[index])
                }
            }
            return 0
        }
    }

    val type: List<Int>
        get() {
            return if (withJoker) {
                typeWithJokers(cards)
            } else {
                typeWithoutJokers(cards)
            }
        }

    private fun typeWithoutJokers(cards: String): List<Int> {
        return cards.groupingBy { it }
            .eachCount()
            .values
            .sortedDescending()
    }

    private fun typeWithJokers(cards: String): List<Int> {
        val noJokers = cards.filterNot { it == 'J' }
        val numJokers = cards.length - noJokers.length

        return if (numJokers == 5) {
            return listOf(5)
        } else {
            val temp = typeWithoutJokers(noJokers)
            listOf(numJokers + temp.first()) + temp.drop(1)
        }
    }

    companion object {

        private val HAND_TYPE = listOf(
            listOf(1, 1, 1, 1, 1),
            listOf(2, 1, 1, 1),
            listOf(2, 2, 1),
            listOf(3, 1, 1),
            listOf(3, 2),
            listOf(4, 1),
            listOf(5),
        )
    }
}




