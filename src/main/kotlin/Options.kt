package processor

import java.lang.IllegalArgumentException

enum class Options(val optionMessage: String, val optionValue: Int) {
    ADD_MATRICES("1. Add matrices", 1),
    MULTIPLY_MATRIX_BY_CONSTANT("2. Multiply matrix by a constant", 2),
    MULTIPLY_MATRICES("3. Multiply matrices", 3),
    TRANSPOSE_MATRIX("4. Transpose matrix", 4),
    CALCULATE_DETERMINANT("5. Calculate a determinant", 5),
    INVERSE_MATRIX("6. Inverse matrix", 6),
    EXIT("0. Exit", 0)
}

fun printOptions() {
    for (option in Options.values()) {
        println(option.optionMessage)
    }
}

enum class TransposeMatrixOptions(val optionMessage: String, val optionValue: Int) {
    MAIN_DIAGONAL("1. Main diagonal", 1),
    SIDE_DIAGONAL("2. Side diagonal", 2),
    VERTICAL_LINE("3. Vertical line", 3),
    HORIZONTAL_LINE("4. Horizontal line", 4),;

    fun toTransposeType(): MatrixOperation.TransposeType {
        return when (this) {
            MAIN_DIAGONAL -> MatrixOperation.TransposeType.MAIN_DIAGONAL
            SIDE_DIAGONAL -> MatrixOperation.TransposeType.SIDE_DIAGONAL
            VERTICAL_LINE -> MatrixOperation.TransposeType.VERTICAL_LINE
            HORIZONTAL_LINE -> MatrixOperation.TransposeType.HORIZONTAL_LINE
        }
    }

}

fun getTransposeMatrixOptionFromInt(position: Int): TransposeMatrixOptions {
    for(option in TransposeMatrixOptions.values()) {
        if (position == option.optionValue) return option
    }
    throw IllegalArgumentException("Enum has no optionValue associated with $position")
}
