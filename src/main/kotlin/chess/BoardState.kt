package io.github.josephsimutis.chess

import io.github.josephsimutis.chess.pieces.*

data class BoardState(private val board: Array<Piece?>) {
    val lightKingCount: Int
        get() = board.count { it == Piece.WHITE_KING }
    val darkKingCount: Int
        get() = board.count { it == Piece.BLACK_KING }
    val gameOver: Boolean
        get() = lightKingCount < 1 || darkKingCount < 1
    val winner: Side?
        get() = if (!gameOver) null
        else if (lightKingCount > darkKingCount) Side.WHITE
        else if (lightKingCount < darkKingCount) Side.BLACK
        else null

    operator fun get(file: Int, rank: Int): Piece? {
        if (!(1..8).contains(file) || !(1..8).contains(rank)) return null
        return board[((rank - 1) * 8) + file - 1]
    }

    operator fun get(notation: String) = fromNotation(notation)?.let { (file, rank) -> this[file, rank] }

    operator fun set(file: Int, rank: Int, piece: Piece?) {
        if (!(1..8).contains(file) || !(1..8).contains(rank)) throw ArrayIndexOutOfBoundsException(
            "Tried to write to invalid square ${
                toNotation(
                    file,
                    rank
                )
            }!"
        )
        board[((rank - 1) * 8) + file - 1] = piece
    }

    operator fun set(notation: String, piece: Piece?) {
        fromNotation(notation)?.also { (file, rank) -> this[file, rank] = piece }
    }

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
        val START = BoardState(Array(64) { index ->
            when {
                index == 0 || index == 7 -> Rook(Side.WHITE)
                index == 1 || index == 6 -> Knight(Side.WHITE)
                index == 2 || index == 5 -> Bishop(Side.WHITE)
                index == 3 -> Queen(Side.WHITE)
                index == 4 -> Piece.WHITE_KING
                (8..15).contains(index) -> Pawn(Side.WHITE)
                index == 56 || index == 63 -> Rook(Side.BLACK)
                index == 57 || index == 62 -> Knight(Side.BLACK)
                index == 58 || index == 61 -> Bishop(Side.BLACK)
                index == 59 -> Queen(Side.BLACK)
                index == 60 -> Piece.BLACK_KING
                (48..55).contains(index) -> Pawn(Side.BLACK)
                else -> null
            }
        })
    }
}