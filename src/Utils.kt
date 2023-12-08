import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

tailrec fun Long.gcd(other: Long): Long = if (other == 0L) this else other.gcd(this % other)

fun Long.lcm(other: Long): Long = (this * other) / gcd(other)
fun Int.lcm(other: Int): Long = this.toLong().lcm(other.toLong())

fun List<Int>.lcm(): Long = map(Int::toLong).reduce { acc, n -> acc.lcm(n) }
