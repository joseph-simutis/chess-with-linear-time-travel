package io.github.josephsimutis.chess.pieces

import io.github.josephsimutis.chess.BoardState
import io.github.josephsimutis.chess.Side
import io.github.josephsimutis.chess.Timeline
import io.github.josephsimutis.chess.moves.CastleMove
import io.github.josephsimutis.chess.moves.EnPassantMove
import io.github.josephsimutis.chess.moves.Move
import io.github.josephsimutis.chess.moves.StandardMove

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
        checkCastle(timeline, index, file, rank, 2)?.apply { moves += this }
        checkCastle(timeline, index, file, rank, -2)?.apply { moves += this }
        return moves.toTypedArray()
    }

    private fun checkCastle(timeline: Timeline, index: Int, file: Int, rank: Int, offset: Int): Move? {
        timeline[index].also { board ->
            if (board[file + (offset / 2), rank] != null) return null
            board[file + offset, rank].also { checking ->
                if (checking is Rook) {
                    if (checking.canCastle && this.canCastle) {
                        return CastleMove(index, rank, if (offset > 0) 8 else 1, offset,).let { move ->
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