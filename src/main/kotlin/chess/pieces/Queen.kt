package io.github.josephsimutis.chess.pieces

import io.github.josephsimutis.chess.moves.Move
import io.github.josephsimutis.chess.Side
import io.github.josephsimutis.chess.Timeline

class Queen(side: Side) : Piece("queen", side) {
    override fun getMoves(timeline: Timeline, index: Int, file: Int, rank: Int): Array<Move> {
        val moves = ArrayList<Move>()
        checkLine(timeline, index, file, rank, 1, 1).apply { moves += this }
        checkLine(timeline, index, file, rank, 0, 1).apply { moves += this }
        checkLine(timeline, index, file, rank, -1, 1).apply { moves += this }
        checkLine(timeline, index, file, rank, 1, 0).apply { moves += this }
        checkLine(timeline, index, file, rank, -1, 0).apply { moves += this }
        checkLine(timeline, index, file, rank, 1, -1).apply { moves += this }
        checkLine(timeline, index, file, rank, 0, -1).apply { moves += this }
        checkLine(timeline, index, file, rank, -1, -1).apply { moves += this }
        return moves.toTypedArray()
    }
}