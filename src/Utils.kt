import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(day: Int, name: String) = Path("inputs/${"%02d".format(day)}/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
  .toString(16)
  .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println(): Any? {
  println(this)
  return this
}

fun <T> T?.validate(other: T) {
  if (this != other) {
    System.err.println("expected $other but got $this")
  }
}

fun printErr(x: String) {
  System.err.println(x)
}

fun String.replaceAt(index: Int, replaceWith: Char): String {
  if (index < 0 || index >= this.length) {
    throw IndexOutOfBoundsException()
  }
  return this.substring(0, index) + replaceWith + this.substring(index + 1)
}


// Function to calculate the greatest common divisor (GCD) using Euclidean algorithm
fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

// Function to calculate the least common multiple (LCM) using the GCD
fun lcm(a: Long, b: Long): Long = if (a == 0L || b == 0L) 0 else (a * b) / gcd(a, b)
