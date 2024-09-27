package io.github.josephsimutis.chess.pieces

import io.github.josephsimutis.chess.BoardState
import io.github.josephsimutis.chess.Move
import io.github.josephsimutis.chess.Side

class Knight(side: Side) : Piece("knight", side) {
    override fun getMoves(board: BoardState, file: Int, rank: Int): Array<Move> {
        val moves = ArrayList<Move>()
        checkMove(board, Move(file, rank, file + 2, rank + 1))?.apply { moves += this }
        checkMove(board, Move(file, rank, file + 2, rank - 1))?.apply { moves += this }
        checkMove(board, Move(file, rank, file - 2, rank + 1))?.apply { moves += this }
        checkMove(board, Move(file, rank, file - 2, rank - 1))?.apply { moves += this }
        checkMove(board, Move(file, rank, file + 1, rank + 2))?.apply { moves += this }
        checkMove(board, Move(file, rank, file + 1, rank - 2))?.apply { moves += this }
        checkMove(board, Move(file, rank, file - 1, rank + 2))?.apply { moves += this }
        checkMove(board, Move(file, rank, file - 1, rank - 2))?.apply { moves += this }
        return moves.toTypedArray()
    }
}