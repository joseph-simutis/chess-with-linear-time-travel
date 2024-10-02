package io.github.josephsimutis.chess

import io.github.josephsimutis.chess.moves.Move
import io.github.josephsimutis.chess.moves.StandardMove

data class Timeline(val history: ArrayList<Pair<Move, BoardState>>, val discontinuousPoints: ArrayList<Int>) {
    constructor() : this(ArrayList(), ArrayList()) { discontinuousPoints += history.lastIndex }

    operator fun get(index: Int): BoardState = history.getOrNull(index)?.second ?: BoardState.START

    operator fun get(index: Int, file: Int, rank: Int) = this[index][file, rank]

    fun getActiveSide(index: Int) = if (index % 2 != 0) Side.WHITE else Side.BLACK

    fun attemptMove(move: Move): Boolean {
        if (!discontinuousPoints.contains(move.index)) return false
        this[move.index].also { board ->
            if (board[move.startFile, move.startRank]?.side == getActiveSide(move.index)) {
                board[move.startFile, move.startRank]?.getMoves(this, move.index, move.startFile, move.startRank)
                    ?.forEach { move2 ->
                        if (move is StandardMove && move2 is StandardMove) {
                            if (move == move2) {
                                move2.applyTo(this)
                                discontinuousPoints.remove(move.index)
                                if (!this[move.index + 1].gameOver) discontinuousPoints += move.index + 1
                                return true
                            }
                        }
                    }
            }
        }
        return false
    }

    fun getHighlightedSquares(index: Int, file: Int, rank: Int): Array<Triple<Int, Int, Int>> {
        val squares = arrayListOf<Triple<Int, Int, Int>>()
        this[index, file, rank]?.also { piece ->
            squares += Triple(index, file, rank)
            piece.getMoves(this, index, file, rank).forEach { move ->
                if (move is StandardMove) {
                    squares += Triple(move.index, move.endRank, move.endFile)
                }
            }
        }
        return squares.toTypedArray()
    }
}