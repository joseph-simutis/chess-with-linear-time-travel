package io.github.josephsimutis.chess.pieces

import io.github.josephsimutis.chess.BoardState
import io.github.josephsimutis.chess.Side

class Queen(side: Side) : Piece("queen", side) {
    override fun getMoves(board: BoardState, file: Int, rank: Int): Array<BoardState> {
        val moves = ArrayList<BoardState>()
        checkLine(board, file, rank, 1, 1).apply { moves += this }
        checkLine(board, file, rank, 0, 1).apply { moves += this }
        checkLine(board, file, rank, -1, 1).apply { moves += this }
        checkLine(board, file, rank, 1, 0).apply { moves += this }
        checkLine(board, file, rank, -1, 0).apply { moves += this }
        checkLine(board, file, rank, 1, -1).apply { moves += this }
        checkLine(board, file, rank, 0, -1).apply { moves += this }
        checkLine(board, file, rank, -1, -1).apply { moves += this }
        return moves.toTypedArray()
    }
}