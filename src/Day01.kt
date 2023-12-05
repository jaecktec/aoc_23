fun main() {
  fun part1(input: List<String>): Int {
    return input.foldRight(0) { line, acc ->
      val joinToString = line.asIterable().filter { it.isDigit() }.map { it.digitToInt() }
      acc + joinToString.first() * 10 + joinToString.last()
    }
  }


  fun part2(input: List<String>): Int {
    val lookupTable = mapOf(
      "one" to 1,
      "two" to 2,
      "three" to 3,
      "four" to 4,
      "five" to 5,
      "six" to 6,
      "seven" to 7,
      "eight" to 8,
      "nine" to 9,
    )

    fun findDigit(line: String, forward: Boolean): Int {
      var index = if(forward) 0 else line.length
      
      var result: Int = 0
      while (if(forward) index < line.length else index-- > 0) {
        val substr = line.substring(index)
        val foundNumber = lookupTable.mapNotNull { (number) ->
          if (substr.startsWith(number)) {
            number
          } else {
            null
          }
        }.firstOrNull()

        if (substr[0].isDigit()) {
          result = substr[0].digitToInt()
          break
        } else if (foundNumber != null) {
          result = lookupTable[foundNumber]!!
          break
        }
        if (forward) {
          index++
        }
      }
      return result
    }
    

    return input.fold(0) { acc, line ->

      val first: Int = findDigit(line, true)
      val last: Int = findDigit(line, false)

      "$line -> $first, $last".println()
      acc + first * 10 + last
    }
  }

  part1(readInput(1, "test_part1")).validate(142)
  part2(readInput(1, "test_part2")).validate(281)

  part1(readInput(1, "input_part1")).println()
  part2(readInput(1, "input_part1")).println()
}
