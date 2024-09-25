package io.github.josephsimutis.chess.pieces

import io.github.josephsimutis.chess.BoardState
import io.github.josephsimutis.chess.Side

class Knight(side: Side) : Piece("knight", side) {
    override fun getMoves(board: BoardState, file: Int, rank: Int): Array<BoardState> {
        val moves = ArrayList<BoardState>()
        return moves.toTypedArray()
    }
}