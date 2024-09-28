package io.github.josephsimutis.chess.pieces

import io.github.josephsimutis.chess.Side
import io.github.josephsimutis.chess.Timeline
import io.github.josephsimutis.chess.moves.Move
import io.github.josephsimutis.chess.moves.StandardMove

class King(side: Side) : Piece("king", side) {
    private var canCastle = true

    override fun getMoves(timeline: Timeline, index: Int, file: Int, rank: Int): Array<Move> {
        val moves = ArrayList<Move>()
        checkMove(timeline, StandardMove(index, file, rank, file + 1, rank + 1))?.apply { moves += this }
        checkMove(timeline, StandardMove(index, file, rank, file, rank + 1))?.apply { moves += this }
        checkMove(timeline, StandardMove(index, file, rank, file - 1, rank + 1))?.apply { moves += this }
        checkMove(timeline, StandardMove(index, file, rank, file + 1, rank))?.apply { moves += this }
        checkMove(timeline, StandardMove(index, file, rank, file - 1, rank))?.apply { moves += this }
        checkMove(timeline, StandardMove(index, file, rank, file + 1, rank - 1))?.apply { moves += this }
        checkMove(timeline, StandardMove(index, file, rank, file, rank - 1))?.apply { moves += this }
        checkMove(timeline, StandardMove(index, file, rank, file - 1, rank - 1))?.apply { moves += this }
        return moves.toTypedArray()
    }

    override fun move() {
        canCastle = false
    }
}