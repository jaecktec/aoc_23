fun main() {
  val day = 8

  data class Node(
    val current: String,
    val right: String,
    val left: String,
  ) {
    override fun toString(): String {
      return current
    }
  }


  fun part1(input: List<String>): Int {
    val nodes = input.drop(2).map {
      val (current, rest) = it.split(" = ")
      val (left, right) = rest.drop(1).dropLast(1).split(", ")
      Node(current, right, left)
    }
    val choiceSequence = input.first().toCharArray()

    var result = 0
    var current = nodes.find { it.current == "AAA" }!!

    while (true) {
      for (choice in choiceSequence) {
        current = if (choice == 'R') {
          nodes.first { it.current == current.right }
        } else {
          nodes.first { it.current == current.left }
        }
        result++
        if (current.current == "ZZZ") {
          return result
        }
      }
    }
  }


  fun part2(input: List<String>): Long {
    val nodes = input.drop(2).map {
      val (current, rest) = it.split(" = ")
      val (left, right) = rest.drop(1).dropLast(1).split(", ")
      Node(current, right, left)
    }
    val choiceSequence = input.first().toCharArray()

    var result = 0L

    var startingNodes = nodes.filter { it.current.endsWith('A') }


    val minStepsForStartingPos: List<Long> = startingNodes.map { startingNode ->
      var node = startingNode
      var steps = 0L
      while (true) {
        for (choice in choiceSequence) {
          node = if (choice == 'R') {
            nodes.first { it.current == node.right }
          } else {
            nodes.first { it.current == node.left }
          }
          steps++
        }
        if (node.current.endsWith('Z')) {
          return@map steps
        }
      }
      return@map -1 // make compiler happy
    }

    return minStepsForStartingPos.fold(1) { acc: Long, l: Long -> lcm(acc, l) }
  }

  part1(readInput(day, "test_1")).validate(2)
  part1(readInput(day, "test_2")).validate(6)
  part2(readInput(day, "test_3")).validate(6)

  part1(readInput(day, "input")).println()
  part2(readInput(day, "input")).println()
}
