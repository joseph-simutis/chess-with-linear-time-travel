package io.github.josephsimutis.chess.pieces

import io.github.josephsimutis.chess.moves.standard.StandardMove
import io.github.josephsimutis.chess.Side
import io.github.josephsimutis.chess.Timeline
import io.github.josephsimutis.chess.moves.Move

abstract class Piece(val pieceName: String, val side: Side) {
    abstract fun getMoves(timeline: Timeline, index: Int, file: Int, rank: Int): Array<Move>

    fun checkMove(timeline: Timeline, move: StandardMove, canMove: Boolean = true, canCapture: Boolean = true) = timeline[move.index].let { board ->
        if (!move.inBounds) null
        else if (canMove && board[move.endFile, move.endRank] == null
            || canCapture && board[move.endFile, move.endRank]?.side == !side
        ) move
        else null
    }

    fun checkLine(timeline: Timeline, index: Int, startFile: Int, startRank: Int, incFile: Int, incRank: Int): Array<StandardMove> {
        var currentFile = startFile
        var currentRank = startRank
        val moves = ArrayList<StandardMove>()
        while (true) {
            currentFile += incFile
            currentRank += incRank
            val move =
                checkMove(timeline, StandardMove(index, startFile, startRank, currentFile, currentRank)) ?: return moves.toTypedArray()
            moves += move
            if (timeline[index, currentFile, currentRank]?.side == !side) {
                return moves.toTypedArray()
            }
        }
    }

    open fun move() {}

    override fun equals(other: Any?) = javaClass == other?.javaClass && side == (other as Piece).side

    override fun hashCode() = 31 * pieceName.hashCode() + side.hashCode()

    companion object {
        val WHITE_KING = King(Side.WHITE)
        val BLACK_KING = King(Side.BLACK)
    }
}