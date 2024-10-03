package io.github.josephsimutis.chess.pieces

import io.github.josephsimutis.chess.Side
import io.github.josephsimutis.chess.Timeline
import io.github.josephsimutis.chess.moves.standard.CastleMove
import io.github.josephsimutis.chess.moves.Move
import io.github.josephsimutis.chess.moves.standard.StandardMove

class King(side: Side) : Piece("king", side) {
    private var canCastle = true

    override fun getMoves(timeline: Timeline, index: Int, file: Int, rank: Int): Array<Move> {
        val moves = ArrayList<Move>()
        checkMove(timeline, StandardMove(index, file, rank, file + 1, rank + 1))?.apply { moves += this }
        checkMove(timeline, StandardMove(index, file, rank, file, rank + 1))?.apply { moves += this }
        checkMove(timeline, StandardMove(index, file, rank, file - 1, rank + 1))?.apply { moves += this }
        checkMove(timeline, StandardMove(index, file, rank, file + 1, rank))?.apply { moves += this }
        checkMove(timeline, StandardMove(index, file, rank, file - 1, rank))?.apply { moves += this }
        checkMove(timeline, StandardMove(index, file, rank, file + 1, rank - 1))?.apply { moves += this }
        checkMove(timeline, StandardMove(index, file, rank, file, rank - 1))?.apply { moves += this }
        checkMove(timeline, StandardMove(index, file, rank, file - 1, rank - 1))?.apply { moves += this }
        checkCastle(timeline, index, file, rank, true)?.apply { moves += this }
        checkCastle(timeline, index, file, rank, false)?.apply { moves += this }
        return moves.toTypedArray()
    }

    private fun checkCastle(timeline: Timeline, index: Int, file: Int, rank: Int, positive: Boolean): Move? {
        timeline[index].also { board ->
            if (board[file + (if (positive) 1 else -1), rank] != null) return null
            board[file + if (positive) 2 else -2, rank].also { checking ->
                if (checking is Rook) {
                    if (checking.canCastle && this.canCastle) {
                        return CastleMove(index, file, rank, if (positive) 8 else 1, positive).let { move ->
                            if (board[move.endFile, move.endRank] == null) move
                            else null
                        }
                    }
                }
            }
        }
        return null
    }

    override fun move() {
        canCastle = false
    }
}