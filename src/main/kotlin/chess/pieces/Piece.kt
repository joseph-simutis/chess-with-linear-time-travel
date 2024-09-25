package io.github.josephsimutis.chess.pieces

import io.github.josephsimutis.chess.BoardState
import io.github.josephsimutis.chess.Side

abstract class Piece(val pieceName: String, val side: Side) {
    abstract fun getMoves(board: BoardState, file: Int, rank: Int): Array<BoardState>

    fun checkMove(
        board: BoardState,
        startFile: Int,
        startRank: Int,
        offsetFile: Int,
        offsetRank: Int,
        move: Boolean = true,
        capture: Boolean = true
    ) = if (move && board[startFile + offsetFile, startRank + offsetRank] == null) board.moved(
        startFile,
        startRank,
        startFile + offsetFile,
        startRank + offsetRank
    )
    else if (capture && board[startFile + offsetFile, startRank + offsetRank]?.side == !side) board.moved(
        startFile,
        startRank,
        startFile + offsetFile,
        startRank + offsetRank
    )
    else null

    fun checkLine(board: BoardState, startFile: Int, startRank: Int, incFile: Int, incRank: Int): Array<BoardState> {
        var currentFile = startFile
        var currentRank = startRank
        val moves = ArrayList<BoardState>()
        while (true) {
            currentFile += incFile
            currentRank += incRank
            val move = checkMove(board, startFile, startRank, currentFile, currentRank) ?: return moves.toTypedArray()
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