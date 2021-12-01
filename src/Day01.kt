fun main() {

    fun part1(input: List<String>): SonarReport {
        return input.map { it.toInt() }
            .fold(SonarReport(-1, 0)) { prevReport, measurement ->
            prevReport.addMeasurement(measurement)
        }
    }

    fun part2(input: List<String>): SonarReport {
        return input.map { it.toInt() }
            .windowed(3)
            .fold(SonarReport(-1, 0)) { prevReport, measurement ->
            prevReport.addMeasurement(measurement.sum())
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput).increases == 7)
    check(part2(testInput).increases == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}

class SonarReport(val increases: Int = 0, private val measurement: Int) {

    fun addMeasurement(newMeasurement: Int): SonarReport {
        return if (newMeasurement > this.measurement) {
            SonarReport(increases + 1, newMeasurement)
        } else {
            SonarReport(increases, newMeasurement)
        }
    }

    override fun toString(): String {
        return "SonarReport(increases=$increases, measurement=$measurement)"
    }

}