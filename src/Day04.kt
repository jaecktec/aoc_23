import kotlin.math.pow

fun main() {

  fun part1(input: List<String>): Int {
    return input.map { line -> line.split(": ")[1] }
      .map { line ->
        val (winning, onCard) = line.split("|")
          .map { numbers -> numbers.trim().split(" ").filter { it.isNotBlank() }.map { it.toInt() } }
        winning.intersect(onCard.toSet()).size
      }.foldRight(0) { winningNumbers, acc ->
        acc + ((1 * 2.0.pow(winningNumbers - 1).toInt()).takeIf { winningNumbers > 0 } ?: 0)
      }
  }

  fun part2(input: List<String>): Int {
    data class Card(
      val index: Int,
      val winning: List<Int>,
      val onCard: List<Int>,
    ) {
      val winningAmount = winning.intersect(onCard.toSet()).size
      override fun toString(): String {
        return "Card(index=$index, winningAmount=$winningAmount)"
      }

    }

    val cards = input
      .map { line -> line.split(": ")[1] }
      .mapIndexed { index, line ->
        val (winning, onCard) = line.split("|")
          .map { numbers -> numbers.trim().split(" ").filter { it.isNotBlank() }.map { it.toInt() } }
        Card(index + 1, winning, onCard)
      }
    
    // probably faster with a map :shrug:
    val iterate = mutableListOf<Int>()
    iterate.addAll(1..cards.size)
    for(card in cards){
      val newCards = (card.index + 1..(card.index + card.winningAmount)).toList()
      val multiplier = iterate.count { it == card.index }
      for (i in 1..multiplier){
        iterate.addAll(newCards)
      }
    }
    
    println(iterate.groupBy { it }.map { Pair(it.key, it.value.size) }.joinToString("\n"))
    return iterate.size
  }

//  part1(readInput(4, "test")).validate(13)
  part2(readInput(4, "test")).validate(30)

//  part1(readInput(4, "input")).println()
  part2(readInput(4, "input")).println()
}
