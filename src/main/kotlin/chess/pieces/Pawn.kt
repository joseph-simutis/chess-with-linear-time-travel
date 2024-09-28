package io.github.josephsimutis.chess.pieces

import io.github.josephsimutis.chess.BoardState
import io.github.josephsimutis.chess.moves.Move
import io.github.josephsimutis.chess.Side
import io.github.josephsimutis.chess.Timeline
import io.github.josephsimutis.chess.moves.EnPassantMove
import io.github.josephsimutis.chess.moves.StandardMove

class Pawn(side: Side) : Piece("pawn", side) {
    private var canDoubleJump = true
    var enPassantPossible = false

    override fun getMoves(timeline: Timeline, index: Int, file: Int, rank: Int): Array<Move> {
        val moves = ArrayList<Move>()
        checkMove(timeline, StandardMove(index, file, rank, file, rank + side.direction), canCapture=false)?.also { move ->
            moves += move
            if (canDoubleJump) checkMove(timeline, StandardMove(index, file, rank, file, rank + side.direction * 2), canCapture=false)?.apply { moves += this }
        }
        checkMove(timeline, StandardMove(index, file, rank, file + 1, rank + side.direction), canMove=false)?.apply { moves += this }
        checkMove(timeline, StandardMove(index, file, rank, file - 1, rank + side.direction), canMove=false)?.apply { moves += this }
        /*checkEnPassant(timeline, index, file, rank, 1)?.apply { moves += this }
        checkEnPassant(timeline, index, file, rank, -1)?.apply { moves += this }*/
        return moves.toTypedArray()
    }

    private fun checkEnPassant(timeline: Timeline, index: Int, file: Int, rank: Int, leftOrRight: Int): Move? {
        timeline[index].also { board ->
            board[file + leftOrRight, rank].also { checking ->
                if (javaClass == checking?.javaClass) {
                    checking as Pawn
                    if (checking.enPassantPossible) {
                        return EnPassantMove(index, file, rank, leftOrRight, side.direction).let { move ->
                            if (!move.inBounds) null
                            else if (board[move.endFile, move.endRank] == null) move
                            else null
                        }
                    }
                }
            }
        }
        return null
    }

    override fun move() {
        if (canDoubleJump) {
            canDoubleJump = false
            enPassantPossible = true
        } else if (enPassantPossible) {
            enPassantPossible = false
        }
    }
}