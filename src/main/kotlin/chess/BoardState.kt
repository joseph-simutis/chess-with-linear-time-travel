package io.github.josephsimutis.chess

import io.github.josephsimutis.chess.pieces.*

data class BoardState(private val board: Array<Piece?>) {
    val hasLightKing: Boolean
        get() = board.contains(Piece.LIGHT_KING)
    val hasDarkKing: Boolean
        get() = board.contains(Piece.DARK_KING)
    val gameOver: Boolean
        get() = !hasLightKing || !hasDarkKing
    val winner: Side?
        get() = if (hasLightKing && !hasDarkKing) Side.LIGHT
        else if (!hasLightKing && hasDarkKing) Side.DARK
        else null

    operator fun get(file: Int, rank: Int): Piece? {
        if (!(1..8).contains(file) || !(1..8).contains(rank)) return null
        return board[((rank - 1) * 8) + file - 1]
    }

    operator fun get(notation: String) = fromNotation(notation)?.also { (file, rank) -> this[file, rank] }

    fun with(file: Int, rank: Int, change: Piece?): BoardState? {
        if (!(1..8).contains(file) || !(1..8).contains(rank)) return null
        return copy().apply {
            board[((rank - 1) * 8) + file - 1] = change
        }
    }

    fun moved(startFile: Int, startRank: Int, endFile: Int, endRank: Int) =
        this.with(endFile, endRank, this[startFile, startRank])?.with(startFile, startRank, null).apply { this?.get(endFile, endRank)?.move() }

    operator fun iterator() = board.iterator()

    override operator fun equals(other: Any?): Boolean {
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
                index == 0 || index == 7 -> Rook(Side.LIGHT)
                index == 1 || index == 6 -> Knight(Side.LIGHT)
                index == 2 || index == 5 -> Bishop(Side.LIGHT)
                index == 3 -> Queen(Side.LIGHT)
                index == 4 -> Piece.LIGHT_KING
                (8..15).contains(index) -> Pawn(Side.LIGHT)
                index == 56 || index == 63 -> Rook(Side.DARK)
                index == 57 || index == 62 -> Knight(Side.DARK)
                index == 58 || index == 61 -> Bishop(Side.DARK)
                index == 59 -> Queen(Side.DARK)
                index == 60 -> Piece.DARK_KING
                (48..55).contains(index) -> Pawn(Side.DARK)
                else -> null
            }
        })
    }
}