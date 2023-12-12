enum class Day07Type(val points: Int) {
  FiveOfAKind(7),
  FourOfAKind(6),
  FullHouse(5),
  ThreeOfAKind(4),
  TwoPairs(3),
  OnePair(2),
  HighCard(1)
}

fun main() {
  val day = 7

  fun determineType(hand: String): Day07Type {
    assert(hand.length == 5) { "hand must be 5 characters" }
    val charCountMap = hand.groupingBy { it }.eachCount()


    return when {
      charCountMap.containsValue(5) -> Day07Type.FiveOfAKind
      charCountMap.containsValue(4) -> Day07Type.FourOfAKind
      charCountMap.containsValue(3) && charCountMap.containsValue(2) -> Day07Type.FullHouse
      charCountMap.containsValue(3) -> Day07Type.ThreeOfAKind
      charCountMap.count { it.value == 2 } == 2 -> Day07Type.TwoPairs
      charCountMap.containsValue(2) -> Day07Type.OnePair
      hand.toSet().size == 5 -> Day07Type.HighCard
      else -> {
        throw IllegalStateException("should not happen")
      }
    }
  }

  fun part1(input: List<String>): Int {
    val strength = listOf('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A')
    val grouped = input
      .map { it.split(" ") }
      .map { (l, r) ->
        Triple(l, r.toInt(), determineType(l))
      }
      .sortedWith { a, b ->
        when {
          a.third.points > b.third.points -> 1
          a.third.points < b.third.points -> -1
          a.third.points == b.third.points -> {
            for (i in 0..5) {
              if (strength.indexOf(a.first[i]) == strength.indexOf(b.first[i])) continue
              if (strength.indexOf(a.first[i]) > strength.indexOf(b.first[i])) return@sortedWith 1
              if (strength.indexOf(a.first[i]) < strength.indexOf(b.first[i])) return@sortedWith -1
            }
            throw IllegalStateException("should not come here")
          }

          else -> 0
        }
      }

    return grouped.foldIndexed(0) { index, acc, triple ->
      acc + (index + 1) * triple.second
    }
  }


  fun part2(input: List<String>): Long {
    // J is weakest - wer lesen kann ist klar im Vorteil..
    val strength = listOf('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A')
    fun determineTypeWithJoker(hand: String): Day07Type {
      if (!hand.contains("J")) return determineType(hand)
      var highestType = determineType(hand)
      for (i in 0 until 5) {
        if (hand[i] != 'J') continue
        for (replacement in strength.drop(1)) {
          val newHand = hand.replaceAt(i, replacement)

          val possibleType = if (newHand.contains('J')) {
            determineTypeWithJoker(newHand)
          } else {
            determineType(hand.replaceAt(i, replacement))
          }

          if (highestType.points < possibleType.points) {
            highestType = possibleType
          }
          if (highestType.points == Day07Type.FiveOfAKind.points) break; // doesn't get higher
        }
      }
      return highestType
    }


    val grouped = input
      .map { it.split(" ") }
      .map { (l, r) ->
        Triple(l, r.toInt(), determineTypeWithJoker(l))
      }
      .sortedWith { a, b ->
        when {
          a.third.points > b.third.points -> 1
          a.third.points < b.third.points -> -1
          a.third.points == b.third.points -> {
            for (i in 0..5) {
              if (strength.indexOf(a.first[i]) == strength.indexOf(b.first[i])) continue
              if (strength.indexOf(a.first[i]) > strength.indexOf(b.first[i])) return@sortedWith 1
              if (strength.indexOf(a.first[i]) < strength.indexOf(b.first[i])) return@sortedWith -1
            }
            throw IllegalStateException("should not come here")
          }

          else -> 0
        }
      }

    return grouped.foldIndexed(0) { index, acc, triple ->
      acc + (index + 1) * triple.second
    }
  }

  part1(readInput(day, "test")).validate(6440)
  part1(readInput(day, "input")).println()

  part2(readInput(day, "test")).validate(5905)
  part2(readInput(day, "input")).println()
}
