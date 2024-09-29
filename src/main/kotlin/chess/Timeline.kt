package io.github.josephsimutis.chess

import io.github.josephsimutis.chess.moves.Move
import io.github.josephsimutis.chess.moves.StandardMove

data class Timeline(val history: ArrayList<Pair<Move, BoardState>>) {
    constructor() : this(ArrayList())

    operator fun get(index: Int): BoardState =
        if (history.isEmpty() || index < 0) BoardState.START
        else if (index > history.lastIndex) history.last().second
        else history[index].second

    fun getActiveSide(index: Int) = if (index % 2 != 0) Side.WHITE else Side.BLACK

    fun attemptMove(move: Move): Boolean {
        this[move.index].also { board ->
            if (board[move.startFile, move.startRank]?.side != getActiveSide(move.index)) return false
            board[move.startFile, move.startRank]?.getMoves(this, move.index, move.startFile, move.startRank)?.forEach { move2 ->
                if (move is StandardMove && move2 is StandardMove) {
                    if (move.equals(move2)) {
                        move2.applyTo(this)
                        return true
                    }
                }
            }
        }
        return false
    }
}