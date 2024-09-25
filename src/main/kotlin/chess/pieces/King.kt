package io.github.josephsimutis.chess.pieces

import io.github.josephsimutis.chess.BoardState
import io.github.josephsimutis.chess.Side

class King(side: Side) : Piece("king", side) {
    private var canCastle = true

    override fun getMoves(board: BoardState, file: Int, rank: Int): Array<BoardState> {
        val moves = ArrayList<BoardState>()
        checkMove(board, file, rank, 1, 1)?.apply { moves += this }
        checkMove(board, file, rank, 0, 1)?.apply { moves += this }
        checkMove(board, file, rank, -1, 1)?.apply { moves += this }
        checkMove(board, file, rank, 1, 0)?.apply { moves += this }
        checkMove(board, file, rank, -1, 0)?.apply { moves += this }
        checkMove(board, file, rank, 1, -1)?.apply { moves += this }
        checkMove(board, file, rank, 0, -1)?.apply { moves += this }
        checkMove(board, file, rank, -1, -1)?.apply { moves += this }
        return moves.toTypedArray()
    }

    override fun move() {
        canCastle = false
    }
}