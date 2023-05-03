package processor

import processor.MatrixOperation.getDeterminant
import processor.MatrixOperation.getInverseMatrix
import processor.MatrixOperation.multiplyMatrixByConstant
import processor.MatrixOperation.sumMatrix
import processor.MatrixOperation.transpose
import processor.MatrixUtils.firstMatrixColumnsEqualsSecondMatrixRows
import processor.PrintHelper.printCleanDouble
import processor.PrintHelper.printMatrix
import processor.PrintHelper.printReadableMatrix

/**
 * Introducir multiples valores separados por un espacio.
 *
 * Al solicitar tamaño introducir filas y columnas separadas por un espacio.
 * Ej:2 2
 *
 * Al solicitar matriz introducir fila separadas por un espacio.
 * Ej:1 5 4 6
 */
fun main() {
    var run = true
    while (run) {
        printOptions()
        print("Your choice: > ")
        when(readln().toInt()) {
            Options.ADD_MATRICES.optionValue -> sum()
            Options.MULTIPLY_MATRIX_BY_CONSTANT.optionValue -> multiplicationByConstant()
            Options.MULTIPLY_MATRICES.optionValue -> multiplyMatrices()
            Options.TRANSPOSE_MATRIX.optionValue -> showTransposeMatrixOptions()
            Options.CALCULATE_DETERMINANT.optionValue -> calculateDeterminant()
            Options.INVERSE_MATRIX.optionValue -> inverseMatrix()
            Options.EXIT.optionValue -> run = false
        }
    }
}

fun inverseMatrix() {
    val matrix = getNotNamedMatrix()
    val matrixDeterminant = getDeterminant(matrix)
    if (matrixDeterminant != 0.0) {
        println("The result is:")
        printReadableMatrix(getInverseMatrix(matrix, matrixDeterminant))
        println()
    } else {
        println("This matrix doesn't have an inverse.")
    }

}

fun calculateDeterminant() {
    val matrix = getNotNamedMatrix()
    println("The result is:")
    printCleanDouble(getDeterminant(matrix))
    println()
}

fun showTransposeMatrixOptions() {
    println()
    for (option in TransposeMatrixOptions.values()) { println(option.optionMessage) }
    print("Your choice: > ")
    transposeMatrix(getTransposeMatrixOptionFromInt(readln().toInt()))
    println()
}

fun transposeMatrix(options: TransposeMatrixOptions) {
    val matrix = getNotNamedMatrix()
    println("The result is:")
    printMatrix(transpose(matrix,options.toTransposeType()))
}

fun multiplyMatrices() {
    val firstMatrix = getNamedMatrix("first")
    val secondMatrix = getNamedMatrix("second")

    if (firstMatrixColumnsEqualsSecondMatrixRows(firstMatrix, secondMatrix)) {
        println("The result is:")
        printMatrix(MatrixOperation.multiplyMatrices(firstMatrix, secondMatrix))
    } else {
        println("The operation cannot be performed")
    }
}

fun multiplicationByConstant() {
    print("Enter size of matrix: > ")
    val (rows, columns) = readRowsAndColumns()
    println("Enter matrix:")
    val matrix = readMatrix(rows, columns)
    print("Enter constant : > ")
    val constant = readln().toDouble()
    println("The result is:")
    printMatrix(multiplyMatrixByConstant(matrix, constant), printAllDecimals = true)
}

fun sum() {

    val firstMatrix = getNamedMatrix("first")
    val secondMatrix = getNamedMatrix("second")

    if (!MatrixUtils.twoMatrixHaveSameRowsAndColumns(firstMatrix, secondMatrix)) {
        println("The operation cannot be performed")
    } else {
        val resultMatrix = sumMatrix(firstMatrix, secondMatrix)
        println("The result is:")
        printMatrix(resultMatrix)
    }
    println()

}