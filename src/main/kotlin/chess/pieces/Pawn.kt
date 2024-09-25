package io.github.josephsimutis.chess.pieces

import io.github.josephsimutis.chess.BoardState
import io.github.josephsimutis.chess.Side

class Pawn(side: Side) : Piece("pawn", side) {
    private var canDoubleJump = false

    override fun getMoves(board: BoardState, file: Int, rank: Int): Array<BoardState> {
        val moves = ArrayList<BoardState>()
        checkMove(board, file, rank, 0, side.direction, capture=false)?.also { move ->
            moves += move
            if (!canDoubleJump) checkMove(board, file, rank, 0, side.direction * 2, capture = false)?.apply { moves += this }
        }
        checkMove(board, file, rank, 1, side.direction, move=false)?.apply { moves += this }
        checkMove(board, file, rank, -1, side.direction, move=false)?.apply { moves += this }
        return moves.toTypedArray()
    }

    override fun move() {
        canDoubleJump = false
    }
}