package io.github.josephsimutis.chess

data class BoardState(private val board: Array<Square>) {
    val hasLightKing: Boolean
        get() = board.contains(Square.LIGHT_KING)
    val hasDarkKing: Boolean
        get() = board.contains(Square.DARK_KING)
    val gameOver : Boolean
        get() = !hasLightKing || !hasDarkKing
    val winner: Side?
        get() = if (hasLightKing && !hasDarkKing) Side.LIGHT
        else if (!hasLightKing && hasDarkKing) Side.DARK
        else null

    operator fun get(file: Int, rank: Int): Square? {
        if (!(1..8).contains(file) || !(1..8).contains(rank)) return null
        return board[((rank - 1) * 8) + file - 1]
    }

    operator fun get(notation: String): Square? {
        if (notation.length != 2) return null
        if (!(97..104).contains(notation[0].code)) return null
        if (!(49..56).contains(notation[1].code)) return null
        return this[notation[0].code - 96, notation[1].code - 48]
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BoardState

        return board.contentDeepEquals(other.board)
    }

    override fun hashCode(): Int {
        return board.contentDeepHashCode()
    }

    companion object {
        // TODO: Try to simplfy.
        val START = BoardState(Array(64) { index ->
            when {
                index == 0 || index == 7 -> Square(Piece.ROOK, Side.LIGHT)
                index == 1 || index == 6 -> Square(Piece.KNIGHT, Side.LIGHT)
                index == 2 || index == 5 -> Square(Piece.BISHOP, Side.LIGHT)
                index == 3 -> Square(Piece.QUEEN, Side.LIGHT)
                index == 4 -> Square.LIGHT_KING
                (8..15).contains(index) -> Square(Piece.PAWN, Side.LIGHT)
                index == 56 || index == 63 -> Square(Piece.ROOK, Side.DARK)
                index == 57 || index == 62 -> Square(Piece.KNIGHT, Side.DARK)
                index == 58 || index == 61 -> Square(Piece.BISHOP, Side.DARK)
                index == 59 -> Square(Piece.QUEEN, Side.DARK)
                index == 60 -> Square.DARK_KING
                (48..55).contains(index) -> Square(Piece.PAWN, Side.DARK)
                else -> Square.NONE
            }
        })
    }
}