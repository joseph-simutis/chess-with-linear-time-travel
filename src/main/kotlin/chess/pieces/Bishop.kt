package io.github.josephsimutis.chess.pieces

import io.github.josephsimutis.chess.moves.Move
import io.github.josephsimutis.chess.Side
import io.github.josephsimutis.chess.Timeline

class Bishop(side: Side) : Piece("bishop", side) {
    override fun getMoves(timeline: Timeline, index: Int, file: Int, rank: Int): Array<Move> {
        val moves = ArrayList<Move>()
        checkLine(timeline, index, file, rank, 1, 1).apply { moves += this }
        checkLine(timeline, index, file, rank, -1, 1).apply { moves += this }
        checkLine(timeline, index, file, rank, 1, -1).apply { moves += this }
        checkLine(timeline, index, file, rank, -1, -1).apply { moves += this }
        return moves.toTypedArray()
    }
}