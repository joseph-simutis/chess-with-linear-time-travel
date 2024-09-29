package io.github.josephsimutis.chess.moves

import io.github.josephsimutis.chess.BoardState
import io.github.josephsimutis.chess.Timeline
import io.github.josephsimutis.chess.pieces.Piece
import io.github.josephsimutis.chess.toNotation

open class StandardMove(index: Int, startFile: Int, startRank: Int, val endFile: Int, val endRank: Int) : Move(index, startFile, startRank) {
    override val inBounds: Boolean
        get() = (1..8).contains(startFile) &&
                (1..8).contains(startRank) &&
                (1..8).contains(endFile) &&
                (1..8).contains(endRank)

    override fun toString() = "${toNotation(startFile, startRank)} -> ${toNotation(endFile, endRank)}"

    override fun hashCode(): Int {
        var result = startFile
        result = 31 * result + startRank
        result = 31 * result + endFile
        result = 31 * result + endRank
        return result
    }

    override fun applyTo(timeline: Timeline) {
        if (!inBounds) throw IndexOutOfBoundsException("Attempted to make move that was out of bounds!")
        timeline[index].also { board ->
            board[startFile, startRank].also { piece ->
                piece?.move()
                Pair(this, board.copy().also { copy -> makeChanges(copy, piece) }).apply {
                    if (index == timeline.history.lastIndex) timeline.history += this
                    else timeline.history[index + 1] = this
                }
            }
        }
    }

    open fun makeChanges(board: BoardState, piece: Piece?) {
        board[endFile, endRank] = piece
        board[startFile, startRank] = null
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is StandardMove) return false

        if (startFile != other.startFile) return false
        if (startRank != other.startRank) return false
        if (endFile != other.endFile) return false
        if (endRank != other.endRank) return false

        return true
    }
}