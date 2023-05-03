package processor

fun getNamedMatrix(name: String): Array<Array<Double>> {
    print("Enter size of $name matrix: > ")
    val (matrixRows, matrixColumns) = readRowsAndColumns()
    println("Enter $name matrix:")
    return readMatrix(matrixRows, matrixColumns)
}

fun getNotNamedMatrix(): Array<Array<Double>> {
    print("Enter matrix size: > ")
    val (matrixRows, matrixColumns) = readRowsAndColumns()
    println("Enter matrix:")
    return readMatrix(matrixRows, matrixColumns)
}

fun readRowsAndColumns(): List<Int> {
    return readln().trim().split(" ").map { it.toInt() }
}

fun readMatrix(rows: Int, columns: Int): Array<Array<Double>> {
    val matrix = MatrixUtils.createMatrix(rows, columns)
    for (n in matrix.indices) {
        print("> ")
        val row = readln().split(" ").map { it.toDouble() }
        for (m in matrix[n].indices) {
            matrix[n][m] = row[m]
        }
    }
    return matrix
}
