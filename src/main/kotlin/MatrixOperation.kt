package processor

private const val INIT_TOTAL_SUM = 0.0

object MatrixOperation {

    fun sumMatrix(firstMatrix: Array<Array<Double>>, secondMatrix: Array<Array<Double>>): Array<Array<Double>> {
        require(
            MatrixUtils.twoMatrixHaveSameRowsAndColumns(
                firstMatrix,
                secondMatrix
            )
        ) { "The two matrix should have same rows and columns" }
        val resultMatrix = MatrixUtils.createMatrix(
            MatrixUtils.getNumberOfRows(firstMatrix),
            MatrixUtils.getNumberOfColumns(secondMatrix)
        )
        for (n in resultMatrix.indices) {
            for (m in resultMatrix[n].indices) {
                val firstMatrixScalar = firstMatrix[n][m]
                val secondMatrixScalar = secondMatrix[n][m]
                resultMatrix[n][m] = firstMatrixScalar + secondMatrixScalar
            }
        }
        return resultMatrix
    }

    fun multiplyMatrixByConstant(matrix: Array<Array<Double>>, numberToMultiply: Double): Array<Array<Double>> {
        val resultMatrix =
            MatrixUtils.createMatrix(MatrixUtils.getNumberOfRows(matrix), MatrixUtils.getNumberOfColumns(matrix))
        for (n in matrix.indices) {
            for (m in matrix[n].indices) {
                resultMatrix[n][m] = matrix[n][m] * numberToMultiply
            }
        }
        return resultMatrix
    }

    fun multiplyMatrices(firstMatrix: Array<Array<Double>>, secondMatrix: Array<Array<Double>>): Array<Array<Double>> {
        val result = MatrixUtils.createMatrix(
            MatrixUtils.getNumberOfRows(firstMatrix),
            MatrixUtils.getNumberOfColumns(secondMatrix)
        )
        for (n in firstMatrix.indices) {
            for (m in secondMatrix[n].indices) {
                var totalSum = INIT_TOTAL_SUM
                for (scalarIndex in firstMatrix[n].indices) {
                    totalSum += firstMatrix[n][scalarIndex] * secondMatrix[scalarIndex][m]
                }
                result[n][m] = totalSum
            }
        }
        return result
    }

    fun transpose(matrix: Array<Array<Double>>, transposeType: TransposeType): Array<Array<Double>> {
        val result =
            MatrixUtils.createMatrix(MatrixUtils.getNumberOfRows(matrix), MatrixUtils.getNumberOfColumns(matrix))
        for (row in matrix.indices) {
            for (column in matrix[row].indices) {
                when (transposeType) {
                    TransposeType.MAIN_DIAGONAL -> {
                        result[row][column] = matrix[column][row]
                    }
                    TransposeType.SIDE_DIAGONAL -> {
                        result[column][row] = matrix[matrix.indices.last - row][matrix[row].indices.last - column]
                    }
                    TransposeType.VERTICAL_LINE -> {
                        result[row][column] = matrix[row][matrix[row].indices.last - column]
                    }
                    TransposeType.HORIZONTAL_LINE -> {
                        result[row][column] = matrix[matrix[row].indices.last - row][column]
                    }
                }
            }
        }
        return result
    }

    fun getDeterminant(matrix: Array<Array<Double>>): Double {
        return when {
            MatrixUtils.isSizeMatrix(matrix, 1) -> return matrix.first().first()
            MatrixUtils.isSizeMatrix(matrix, 2) -> return get2x2Determinant(matrix)
            else -> {
                val mostZeroVector = MatrixUtils.findRowOrColumnWithMostZeros(matrix)
                val vector = if (mostZeroVector.locationType == MatrixUtils.MatrixLocationType.ROW) {
                    MatrixUtils.getMatrixRow(matrix, mostZeroVector.location)
                } else {
                    MatrixUtils.getMatrixColumn(matrix, mostZeroVector.location)
                }
                val resultList = mutableListOf<Double>()
                for (vectorIndex in vector.indices) {
                    val (row, column) = when (mostZeroVector.locationType) {
                        MatrixUtils.MatrixLocationType.ROW -> listOf(mostZeroVector.location, vectorIndex)
                        MatrixUtils.MatrixLocationType.COLUMN -> listOf(vectorIndex, mostZeroVector.location)
                    }

                    var currentScalar = matrix[row][column]
                    if (currentScalar == 0.0) {
                        resultList.add(0.0)
                    } else {
                        currentScalar = MatrixUtils.getPositionSign(MatrixUtils.MatrixPosition(row, column))
                            .multiplyBySign(currentScalar)
                        val preMinor = MatrixUtils.deleteRowAndColumn(matrix, row, column)
                        resultList.add(currentScalar * getDeterminant(preMinor))
                    }
                }
                resultList.sum()
            }
        }
    }

    private fun get2x2Determinant(matrix: Array<Array<Double>>): Double {
        val firstDiagonal = matrix[0][0] * matrix[1][1]
        val secondDiagonal = matrix[0][1] * matrix[1][0]
        return firstDiagonal - secondDiagonal
    }

    fun getInverseMatrix(matrix: Array<Array<Double>>, determinant: Double): Array<Array<Double>> {
        val transposedMatrix = transpose(matrix, TransposeType.MAIN_DIAGONAL)
        val resultMatrix = MatrixUtils.createMatrix(
            MatrixUtils.getNumberOfRows(transposedMatrix),
            MatrixUtils.getNumberOfColumns(transposedMatrix)
        )
        for (row in transposedMatrix.indices) {
            for (column in transposedMatrix[row].indices) {
                val cofactor = getCofactor(transposedMatrix, row, column)
                resultMatrix[row][column] = cofactor / determinant
            }
        }
        return resultMatrix
    }

    private fun getCofactor(matrix: Array<Array<Double>>, rowIndex: Int, columnIndex: Int): Double {
        val preMinor = MatrixUtils.deleteRowAndColumn(matrix, rowIndex, columnIndex)
        return MatrixUtils.getPositionSign(MatrixUtils.MatrixPosition(rowIndex, columnIndex)).multiplyBySign(getDeterminant(preMinor))
    }

    enum class TransposeType {
        MAIN_DIAGONAL,
        SIDE_DIAGONAL,
        VERTICAL_LINE,
        HORIZONTAL_LINE
    }

}