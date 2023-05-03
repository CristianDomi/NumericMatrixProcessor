package processor

const val INIT_MATRIX_VALUE = 0.0

object MatrixUtils {

    fun createMatrix(rows: Int, columns: Int) = Array(rows) { Array(columns) { INIT_MATRIX_VALUE } }

    fun twoMatrixHaveSameRowsAndColumns(
        firstMatrix: Array<Array<Double>>,
        secondMatrix: Array<Array<Double>>
    ): Boolean {
        return getNumberOfRows(firstMatrix) == getNumberOfRows(secondMatrix) &&
                getNumberOfColumns(firstMatrix) == getNumberOfColumns(secondMatrix)
    }

    fun firstMatrixColumnsEqualsSecondMatrixRows(
        firstMatrix: Array<Array<Double>>,
        secondMatrix: Array<Array<Double>>
    ): Boolean {
        return getNumberOfColumns(firstMatrix) == getNumberOfRows(secondMatrix)
    }

    fun isSizeMatrix(matrix: Array<Array<Double>>, size: Int): Boolean {
        return getNumberOfRows(matrix) == size && getNumberOfColumns(matrix) == size
    }

    fun getNumberOfRows(matrix: Array<Array<Double>>) = matrix.size

    fun getNumberOfColumns(matrix: Array<Array<Double>>) = matrix.first().size

    fun deleteRowAndColumn(matrix: Array<Array<Double>>, rowIndex: Int, columnIndex: Int): Array<Array<Double>> {
        val resultMatrix = createMatrix(getNumberOfRows(matrix) - 1, getNumberOfColumns(matrix) - 1)
        var resultMatrixRowIndex = 0
        var resultMatrixColumnIndex = 0

        for (row in matrix.indices) {
            if (row == rowIndex) continue
            for (column in matrix[row].indices) {
                if (column == columnIndex) continue
                resultMatrix[resultMatrixRowIndex][resultMatrixColumnIndex] = matrix[row][column]
                resultMatrixColumnIndex++
            }
            resultMatrixColumnIndex = 0
            resultMatrixRowIndex++
        }
        return resultMatrix
    }

    fun getPositionSign(position: MatrixPosition): Sign {
        return if ((position.nonZeroMatrixIndexColumn() + position.nonZeroMatrixIndexRow()) % 2 == 0) {
            Sign.POSITIVE
        } else {
            Sign.NEGATIVE
        }
    }

    fun findRowOrColumnWithMostZeros(matrix: Array<Array<Double>>): MatrixMaxZerosLocation {

        val maxZeroRow = MatrixMaxZerosLocation(MatrixLocationType.ROW)
        val maxZeroColumn = MatrixMaxZerosLocation(MatrixLocationType.COLUMN)
        var maxZeroes = 0

        //Filas
        for (row in 0 until getNumberOfRows(matrix)) {
            var maxRowZeros = 0
            for (column in 0 until getNumberOfColumns(matrix)) {
                if (matrix[row][column] == 0.0) maxRowZeros++
                if (maxRowZeros > maxZeroes) {
                    maxZeroes = maxRowZeros
                    maxZeroRow.location = row
                    maxZeroRow.locationType = MatrixLocationType.ROW
                    maxZeroRow.zeros = maxRowZeros
                }
            }
        }

        //Columnas
        for (column in 0 until getNumberOfColumns(matrix)) {
            var maxColumnZeros = 0
            for (row in 0 until getNumberOfRows(matrix)) {
                if (matrix[row][column] == 0.0) maxColumnZeros++
                if (maxColumnZeros > maxZeroes) {
                    maxZeroes = maxColumnZeros
                    maxZeroColumn.location = column
                    maxZeroColumn.locationType = MatrixLocationType.COLUMN
                    maxZeroColumn.zeros = maxColumnZeros
                }
            }
        }

        return if (maxZeroRow.zeros > maxZeroColumn.zeros) maxZeroRow else maxZeroColumn

    }

    fun getMaxCharByColumns(matrix: Array<Array<Double>>): List<Int> {
        val resultList = mutableListOf<Int>()
        val numberOfColumns = getNumberOfColumns(matrix)
        for (columnIndex in 0 until  numberOfColumns) {
            val column = getMatrixColumn(matrix, columnIndex)
            var maxChar = 0
            for (scalar in column) {
                val currentScalarLength = PrintHelper.readableDoubleFormat.format(scalar).toString().length
                if (currentScalarLength > maxChar) {
                    maxChar = currentScalarLength
                }
            }
            resultList.add(maxChar)
        }
        return resultList
    }

    fun getMatrixRow(matrix: Array<Array<Double>>, rowIndex: Int): MutableList<Double> {
        val row = mutableListOf<Double>()
        for (column in 0 until getNumberOfColumns(matrix)) {
            row.add(matrix[rowIndex][column])
        }
        return row
    }

    fun getMatrixColumn(matrix: Array<Array<Double>>, columnIndex: Int): MutableList<Double> {
        val column = mutableListOf<Double>()
        for (row in 0 until getNumberOfRows(matrix)) {
            column.add(matrix[row][columnIndex])
        }
        return column
    }

    data class MatrixMaxZerosLocation(
        var locationType: MatrixLocationType,
        var location: Int = 0,
        var zeros: Int = 0
    )

    data class MatrixPosition(
        val row: Int,
        val column: Int
    ) {
        fun nonZeroMatrixIndexRow() = row + 1
        fun nonZeroMatrixIndexColumn() = column + 1
    }

    enum class MatrixLocationType {
        ROW, COLUMN
    }

    enum class Sign {
        POSITIVE,
        NEGATIVE;

        fun multiplyBySign(number: Double): Double {
            return when (this) {
                POSITIVE -> number * 1
                NEGATIVE -> number * -1
            }
        }
    }

}




