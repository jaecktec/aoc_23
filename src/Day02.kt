fun main() {
  fun part1(input: List<String>): Int {
    val games = input.map { line ->
      val (game, rest) = line.split(":")
      val gameId = game.replace("Game ", "").toInt()
      Pair(gameId, rest
        .split(";")
        .flatMap { it.split(",") }
        .map { it.trim() }
        .map {
          val (number, color) = it.split(" ")
          Pair(color, number.toInt())
        }
        .groupBy { it.first }
        .mapValues { it.value.map { value -> value.second } }
        .mapValues { it.value.max() })
    }

    val relevantGames = games.filter {
      it.second["red"]!! <= 12 &&
          it.second["green"]!! <= 13 &&
          it.second["blue"]!! <= 14
    }

    println(relevantGames)

    return relevantGames.foldRight(0) { game, acc ->
      acc + game.first
    }
  }


  fun part2(input: List<String>): Int {
    val games = input.map { line ->
      val (game, rest) = line.split(":")
      val gameId = game.replace("Game ", "").toInt()
      Pair(gameId, rest
        .split(";")
        .flatMap { it.split(",") }
        .map { it.trim() }
        .map {
          val (number, color) = it.split(" ")
          Pair(color, number.toInt())
        }
        .groupBy { it.first }
        .mapValues { it.value.map { value -> value.second } }
        .mapValues { it.value.max() })
    }

    println(games)

    return games.foldRight(0) { game, acc ->
      acc + game.second["red"]!! *
          game.second["green"]!! *
          game.second["blue"]!!
    }
  }

//  part1(readInput(2, "test")).validate(8)
  part2(readInput(2, "test")).validate(2286)

  part1(readInput(2, "input")).println()
  part2(readInput(2, "input")).println()
}
