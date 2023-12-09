fun main() {
  data class Range(
    val destinationStart: Long,
    val sourceStart: Long,
    val length: Long,
  ) {
    val sourceEnd = sourceStart + length
  }

  fun calculateSeedNumber(groups: Map<String, List<Range>>, seed: Long): Long {
    var key = "seed"
    var mappingKey = groups.keys.find { it.startsWith("$key-to-") }
    var number = seed
    while (mappingKey != null) {
      val mappings = groups.get(mappingKey)!!
      val nextKey = mappingKey.split("-to-")[1]
      val mapping = mappings.find { it.sourceStart <= number && number < it.sourceEnd } ?: Range(0, 0, 0)
      val nextNumber = mapping.destinationStart - mapping.sourceStart + number
      key = nextKey
      number = nextNumber
      mappingKey = groups.keys.find { it.startsWith("$key-to-") }
    }
    return number
  }

  fun parseGroups(input: List<String>): Map<String, List<Range>> {
    val groups: Map<String, List<Range>> = input
      .drop(2)
      .joinToString("\n")
      .split("\n\n")
      .map { it.trim().lines() }
      .filter { it.isNotEmpty() }
      .groupBy { it[0].replace(" map:", "") }
      .mapValues {
        it.value.first().drop(1)
          .map { a ->
            val values = a.split(" ").map { b -> b.toLong() }
            Range(
              destinationStart = values[0],
              sourceStart = values[1],
              length = values[2]
            )
          }
      }
    return groups
  }

  fun part1(input: List<String>): Long {

    val seeds = input.first().split(": ")[1].split(" ").map { it.toLong() }
    val groups: Map<String, List<Range>> = parseGroups(input)

    var lowest: Long = Long.MAX_VALUE
    for (seed in seeds) {
      val number = calculateSeedNumber(groups, seed)
      if (number < lowest) {
        lowest = number
      }
    }
    return lowest
  }


  fun part2(input: List<String>): Long {
    val groups: Map<String, List<Range>> = parseGroups(input)

    var lowest: Long = Long.MAX_VALUE

    val cache = mutableSetOf<Long>()

    for (pair in input.first().split(": ")[1].split(" ").map { it.toLong() }.chunked(2)) {
      for (seed in pair[0]..(pair[0] + pair[1])) {
        if(!cache.add(seed)) continue
        val number = calculateSeedNumber(groups, seed)
        if (number < lowest) {
          lowest = number
        }
      }
    }

    return lowest
  }

//  part1(readInput(5, "test")).validate(35)
  part2(readInput(5, "test")).validate(46)

//  part1(readInput(5, "input")).println()
  part2(readInput(5, "input")).println()
}
