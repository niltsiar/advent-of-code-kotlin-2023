fun main() {
    fun part1(input: List<String>): Int {
        val (instructions, nodes) = parseInput(input)
        return countSteps(instructions, nodes, nodes.getValue("AAA")) { it.name == "ZZZ" }
    }

    fun part2(input: List<String>): Long {
        val (instructions, nodes) = parseInput(input)

        return nodes.keys.filter { it.last() == 'A' }
            .map { key ->
                countSteps(instructions, nodes, nodes.getValue(key)) { it.name.endsWith('Z') }
            }
            .lcm()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test_1")
    check(part1(testInput) == 2)
    check(part1(readInput("Day08_test_2")) == 6)
    check(part2(readInput("Day08_test_3")) == 6L)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
    check(part1(input) == 24253)
    check(part2(input) == 12357789728873)
}

private data class Node(
    val name: String,
    val left: String,
    val right: String,
)

private fun parseInput(input: List<String>): Pair<String, Map<String, Node>> {
    val instructions = input.first()

    val nodes = input.drop(2)
        .map { line ->
            val name = line.substringBefore("=").trim()
            val left = line.substringAfter("(").substringBefore(",")
            val right = line.substringAfter(", ").substringBefore(")")
            Node(name, left, right)
        }
        .associateBy { it.name }

    return instructions to nodes
}

private fun countSteps(
    instructions: String,
    nodes: Map<String, Node>,
    startingNode: Node,
    endCondition: (Node) -> Boolean,
): Int {
    var done = false
    return generateSequence(0, Int::inc)
        .takeWhile { !done }
        .map { instructions[it % instructions.length] }
        .foldIndexed(startingNode to 0) { index, acc, instruction ->
            val node = acc.first
            if (endCondition(node)) {
                done = true
                return@foldIndexed acc.first to index
            }
            when (instruction) {
                'L' -> nodes.getValue(node.left) to index
                else -> nodes.getValue(node.right) to index
            }
        }.second
}
