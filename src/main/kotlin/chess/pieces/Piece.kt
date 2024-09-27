package io.github.josephsimutis.chess.pieces

import io.github.josephsimutis.chess.BoardState
import io.github.josephsimutis.chess.Move
import io.github.josephsimutis.chess.Side

abstract class Piece(val pieceName: String, val side: Side) {
    abstract fun getMoves(board: BoardState, file: Int, rank: Int): Array<Move>

    fun checkMove(board: BoardState, move: Move, canMove: Boolean = true, canCapture: Boolean = true) =
        if (!move.inBounds) null
        else if (canMove && board[move.endFile, move.endRank] == null
            || canCapture && board[move.endFile, move.endRank]?.side == !side
        ) move
        else null

    fun checkLine(board: BoardState, startFile: Int, startRank: Int, incFile: Int, incRank: Int): Array<Move> {
        var currentFile = startFile
        var currentRank = startRank
        val moves = ArrayList<Move>()
        while (true) {
            currentFile += incFile
            currentRank += incRank
            val move =
                checkMove(board, Move(startFile, startRank, currentFile, currentRank)) ?: return moves.toTypedArray()
            moves += move
        }
    }

    open fun move() {}

    override fun equals(other: Any?) = javaClass == other?.javaClass && side == (other as Piece).side

    override fun hashCode() = 31 * pieceName.hashCode() + side.hashCode()

    companion object {
        val LIGHT_KING = King(Side.LIGHT)
        val DARK_KING = King(Side.DARK)
    }
}