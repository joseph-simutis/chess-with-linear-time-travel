package io.github.josephsimutis.chess.pieces

import io.github.josephsimutis.chess.BoardState
import io.github.josephsimutis.chess.Move
import io.github.josephsimutis.chess.Side

class Pawn(side: Side) : Piece("pawn", side) {
    private var canDoubleJump = true
    var enPassantPossible = false

    override fun getMoves(board: BoardState, file: Int, rank: Int): Array<Move> {
        val moves = ArrayList<Move>()
        checkMove(board, Move(file, rank, file, rank + side.direction), canCapture=false)?.also { move ->
            moves += move
            if (canDoubleJump) checkMove(board, Move(file, rank, file, rank + side.direction * 2), canCapture=false)?.apply { moves += this }
        }
        checkMove(board, Move(file, rank, file + 1, rank + side.direction), canMove=false)?.apply { moves += this }
        checkMove(board, Move(file, rank, file - 1, rank + side.direction), canMove=false)?.apply { moves += this }
        board[file + 1, rank].also { checking ->
            if (javaClass == checking?.javaClass) {
                checking as Pawn
                if (checking.enPassantPossible) checkMove(board, Move(file, rank, file + 1, rank + side.direction))?.also { move ->
                    move.additionalEffects = { board -> board.apply { this[file + 1, rank] = null }}
                    moves += move
                }
            }
        }
        checkEnPassant(board, file, rank, 1)
        checkEnPassant(board, file, rank, -1)
        return moves.toTypedArray()
    }

    private fun checkEnPassant(board: BoardState, file: Int, rank: Int, leftOrRight: Int): Move? {
        board[file + leftOrRight, rank].also { checking ->
            if (javaClass == checking?.javaClass) {
                checking as Pawn
                if (checking.enPassantPossible) checkMove(board, Move(file, rank, file + 1, rank + side.direction))?.also { move ->
                    move.additionalEffects = { board -> board.apply { this[file + 1, rank] = null }}
                    return move
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