import kotlin.math.roundToInt
import kotlin.math.sqrt

fun main() {
  val day = 6


  fun part1(input: List<Pair<Long, Long>>): Long {
    // 7ms 9mm
    // t_b = time pushed the button
    // d = distance
    // T = duration of the race
    // d = (T - t) * t
    // => t = 1/2 (T - sqrt(-4 d + T^2))
    return input.map { timeDuration ->
      val (time, distance) = timeDuration
      val minimum = (0.5 * (time - sqrt(time * time - 4 * distance.toDouble())) + 0.5).roundToInt()
      val numSolutions = time - minimum * 2 + 1
      numSolutions
    }.foldRight(1L) { i, acc ->
      i * acc
    }
  }


  fun part2(input: List<String>): Int {
    return 0
  }

  part1(
    listOf(
      // Time to Distance
      Pair(7, 9),
      Pair(15, 40),
      Pair(30, 200),
    )
  ).validate(288)
  part1(
    listOf(
      // Time to Distance
      Pair(71530, 940200),
    )
  ).validate(71503)

  part1(
    listOf(
      // Time to Distance
      Pair(45, 295),
      Pair(98, 1734),
      Pair(83, 1278),
      Pair(73, 1210),
    )
  ).println()
  
  part1(
    listOf(
      // Time to Distance
      Pair(45988373, 295173412781210),
    )
  ).println()
}
