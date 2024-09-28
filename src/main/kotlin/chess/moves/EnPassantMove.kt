package io.github.josephsimutis.chess.moves

import io.github.josephsimutis.chess.BoardState
import io.github.josephsimutis.chess.pieces.Piece

class EnPassantMove(index: Int, startFile: Int, startRank: Int, leftOrRight: Int, direction: Int) : StandardMove(index, startFile, startRank, startFile + leftOrRight, startRank + direction) {
    override fun makeChanges(board: BoardState, piece: Piece?) {
        super.makeChanges(board, piece)
        board[endFile, startRank] = null
    }
}