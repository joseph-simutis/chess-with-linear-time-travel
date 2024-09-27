package io.github.josephsimutis.chess

import io.github.josephsimutis.chess.pieces.*

data class BoardState(private val board: Array<Piece?>) {
    val lightKingCount: Int
        get() = board.count { it == Piece.LIGHT_KING }
    val darkKingCount: Int
        get() = board.count { it == Piece.DARK_KING }
    val gameOver: Boolean
        get() = lightKingCount < 1 || darkKingCount < 1
    val winner: Side?
        get() = if (!gameOver) null
        else if (lightKingCount > darkKingCount) Side.LIGHT
        else if (lightKingCount < darkKingCount) Side.DARK
        else null

    operator fun get(file: Int, rank: Int): Piece? {
        if (!(1..8).contains(file) || !(1..8).contains(rank)) return null
        //println("$file, $rank = ${board[((rank - 1) * 8) + file - 1]}")
        return board[((rank - 1) * 8) + file - 1]
    }

    operator fun get(notation: String) = fromNotation(notation)?.also { (file, rank) -> this[file, rank] }

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

    fun with(move: Move): BoardState {
        if (!move.inBounds) throw IndexOutOfBoundsException("Attempted to make move that was out of bounds!")
        val piece = this[move.startFile, move.startRank]
        piece?.move()
        return copy().also { copy ->
            copy[move.endFile, move.endRank] = piece
            copy[move.startFile, move.startRank] = null
            move.additionalEffects(copy)
        }
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