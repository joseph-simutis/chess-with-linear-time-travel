package io.github.josephsimutis.chess

enum class Side(val sideName: String?, val direction: Int) {
    WHITE("white", 1),
    BLACK("black", -1);

    operator fun not() = if (this == WHITE) BLACK else WHITE
}