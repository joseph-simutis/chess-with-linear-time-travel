package io.github.josephsimutis.chess.pieces

import io.github.josephsimutis.chess.BoardState
import io.github.josephsimutis.chess.Move
import io.github.josephsimutis.chess.Side

class King(side: Side) : Piece("king", side) {
    private var canCastle = true

    override fun getMoves(board: BoardState, file: Int, rank: Int): Array<Move> {
        val moves = ArrayList<Move>()
        checkMove(board, Move(file, rank, file + 1, rank + 1))?.apply { moves += this }
        checkMove(board, Move(file, rank, file, rank + 1))?.apply { moves += this }
        checkMove(board, Move(file, rank, file - 1, rank + 1))?.apply { moves += this }
        checkMove(board, Move(file, rank, file + 1, rank))?.apply { moves += this }
        checkMove(board, Move(file, rank, file - 1, rank))?.apply { moves += this }
        checkMove(board, Move(file, rank, file + 1, rank - 1))?.apply { moves += this }
        checkMove(board, Move(file, rank, file, rank - 1))?.apply { moves += this }
        checkMove(board, Move(file, rank, file - 1, rank - 1))?.apply { moves += this }
        return moves.toTypedArray()
    }

    override fun move() {
        canCastle = false
    }
}