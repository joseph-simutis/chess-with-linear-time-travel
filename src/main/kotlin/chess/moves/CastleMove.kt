package io.github.josephsimutis.chess.moves

import io.github.josephsimutis.chess.BoardState
import io.github.josephsimutis.chess.pieces.Piece

class CastleMove(index: Int, rank: Int, private val rookFile: Int, private val offset: Int) : StandardMove(index, 5, rank, 5 + offset, rank) {
    override fun makeChanges(board: BoardState, piece: Piece?) {
        super.makeChanges(board, piece)
        val rook = board[rookFile, startRank]
        rook?.move()
        board[endFile - (offset / 2), endRank] = rook
        board[rookFile, startRank] = null
    }

    override fun toString() = if (offset > 0) "O-O" else "O-O-O"
}