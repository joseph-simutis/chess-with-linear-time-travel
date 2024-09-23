package io.github.josephsimutis.chess

data class Square(val piece: Piece?, val side: Side?) {
    companion object {
        val NONE = Square(null, null)
        val LIGHT_KING = Square(Piece.KING, Side.LIGHT)
        val DARK_KING = Square(Piece.KING, Side.DARK)
    }
}