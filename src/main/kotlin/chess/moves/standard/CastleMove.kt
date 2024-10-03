package io.github.josephsimutis.chess.moves.standard

import io.github.josephsimutis.chess.BoardState
import io.github.josephsimutis.chess.Timeline
import io.github.josephsimutis.chess.pieces.Piece

class CastleMove(index: Int, file: Int, rank: Int, private val rookFile: Int, private val positive: Boolean) : StandardMove(index, file, rank, file + if (positive) 2 else -2, rank) {
    private val offset: Int
        get() = if (positive) 2 else -2

    override fun makeChanges(board: BoardState, piece: Piece?) {
        super.makeChanges(board, piece)
        val rook = board[rookFile, startRank]
        board[endFile - (offset / 2), endRank] = rook
        board[rookFile, startRank] = null
    }

    override fun toNotation(timeline: Timeline) = if (positive) "O-O" else "O-O-O"
}