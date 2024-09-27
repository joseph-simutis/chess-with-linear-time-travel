package io.github.josephsimutis.chess.pieces

import io.github.josephsimutis.chess.BoardState
import io.github.josephsimutis.chess.Move
import io.github.josephsimutis.chess.Side

class Queen(side: Side) : Piece("queen", side) {
    override fun getMoves(board: BoardState, file: Int, rank: Int): Array<Move> {
        val moves = ArrayList<Move>()
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