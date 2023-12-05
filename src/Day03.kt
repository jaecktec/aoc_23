import kotlin.math.max
import kotlin.math.min

fun main() {

  data class Gear(
    val line: Int,
    val from: Int,
    val to: Int,
    val value: Int,
  )

  fun getGears(
    input: List<String>,
    lineLength: Int,
  ): List<Gear> {
    val pointsOfInterests = mutableListOf<Gear>()
    for ((lineIndex, line) in input.withIndex()) {
      var poiFrom: Int? = null
      var poiValue: String = ""
      for ((charIndex, char) in line.toCharArray().withIndex()) {
        if (char.isDigit()) {
          if (poiFrom == null) {
            poiFrom = charIndex
          }
          poiValue += char
        } else if (char.isDigit().not() && poiFrom != null) {
          pointsOfInterests.add(
            Gear(
              line = lineIndex,
              from = poiFrom,
              to = charIndex - 1,
              value = poiValue.toInt()
            )
          )
          // reset
          poiFrom = null
          poiValue = ""
        }
      }
      if (poiFrom != null) {
        pointsOfInterests.add(
          Gear(
            line = lineIndex,
            from = poiFrom,
            to = lineLength - 1,
            value = poiValue.toInt()
          )
        )
      }
    }
    return pointsOfInterests
  }

  fun part1(input: List<String>): Int {

    val lineLength = input[0].length


    fun isPointRelevant(pointOfInterest: Gear): Boolean {
      val charFrom = max(0, pointOfInterest.from - 1)
      val charTo = min(lineLength - 1, pointOfInterest.to + 1)
      val lineFrom = max(0, pointOfInterest.line - 1)
      val lineTo = min(input.size - 1, pointOfInterest.line + 1)


      for (charIndex in charFrom..charTo) {
        for (lineIndex in lineFrom..lineTo) {
          val line = input[lineIndex]
          if (line[charIndex].isDigit().not() && line[charIndex] != '.') {
            return true
          }
        }
      }
      return false
    }

    val gears = getGears(input, lineLength)

    return gears.filter { isPointRelevant(it) }.foldRight(0) { pointOfInterest, acc ->
      acc + pointOfInterest.value
    }
  }

  fun part2(input: List<String>): Int {

    val lineLength = input[0].length

    data class GearPoint(
      val line: Int,
      val column: Int,
    )
    
    data class Transmission(
      val gearPoint: GearPoint,
      val firstGear: Gear,
      val secondGear: Gear
    )
    
    val gearsPoints = input.flatMapIndexed { lineIndex: Int, line: String ->
      line.toCharArray().mapIndexed { charIndex, char ->
        Pair(lineIndex, charIndex).takeIf { char == '*' }
      }
    }.filterNotNull()

    val gears = getGears(input, lineLength)

    val completeTransmissions = gearsPoints.mapNotNull { (gearPointLineIndex, gearPointCharIndex) ->

      val searchPoints = (-1..1).toList().flatMap { lineRelativeIndex ->
        (-1..1).toList().map { charRelativeIndex ->
          when {
            charRelativeIndex == 0 && lineRelativeIndex == 0 -> null
            gearPointCharIndex + charRelativeIndex < 0 || gearPointCharIndex + charRelativeIndex >= lineLength -> null
            gearPointLineIndex + lineRelativeIndex < 0 || gearPointLineIndex + lineRelativeIndex >= input.size -> null
            else -> Pair(gearPointLineIndex + lineRelativeIndex, gearPointCharIndex + charRelativeIndex)
          }
        }
      }.filterNotNull()

      val relevantGears = gears.filter { gear ->
        (gear.from..gear.to).any { charIndex -> searchPoints.contains(Pair(gear.line, charIndex)) }
      }
      if (relevantGears.size < 2) {
        null
      } else {
        if (relevantGears.size > 2) {
          println("we have too many relevant gears 0o")
        }
        Transmission(GearPoint(gearPointLineIndex, gearPointCharIndex), relevantGears[0], relevantGears[1])
      }
    }

    return completeTransmissions.foldRight(0){ transmission, acc ->
      acc + transmission.firstGear.value * transmission.secondGear.value
    }
  }

  part1(readInput(3, "test")).validate(4361)
  part2(readInput(3,  "test")).validate(467835)

  part1(readInput(3, "input")).println()
  part2(readInput(3, "input")).println()
}
