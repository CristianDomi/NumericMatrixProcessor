package processor

import java.math.RoundingMode
import java.text.DecimalFormat

private const val NON_DECIMAL_THRESHOLD = 1
private const val DECIMAL_THRESHOLD = 0.0

object PrintHelper {

    val readableDoubleFormat  = DecimalFormat("#.##").apply { roundingMode = RoundingMode.DOWN }

    fun printReadableMatrix(matrix: Array<Array<Double>>) {
        val maxChars = MatrixUtils.getMaxCharByColumns(matrix)
        for (row in matrix.indices) {
            for (column in matrix.indices) {
                val valueToPrint = if (matrix[row][column] == 0.0) {
                    "0"
                } else {
                    readableDoubleFormat.format(matrix[row][column]) //+ " ")
                }
                if (valueToPrint.length < maxChars[column]) {
                    repeat(maxChars[column] - valueToPrint.length) {
                        print(" ")
                    }
                }
                print("$valueToPrint ")
            }
            println()
        }
    }

    fun printMatrix(matrix: Array<Array<Double>>, printAllDecimals: Boolean = false) {
        for (n in matrix.indices) {
            for (m in matrix[n].indices) {
                val currentValue = matrix[n][m]
                if (printAllDecimals) {
                    print("$currentValue ")
                } else {
                    printCleanDouble(currentValue)
                }
            }
            println()
        }
    }

    fun printCleanDouble(number: Double) {
        if (number % NON_DECIMAL_THRESHOLD != DECIMAL_THRESHOLD) {
            print("$number ")
        } else {
            print("%.0f".format(number) + " ")
        }
    }

}

