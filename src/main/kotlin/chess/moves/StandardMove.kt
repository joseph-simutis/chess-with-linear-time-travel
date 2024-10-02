package io.github.josephsimutis.chess.moves

import io.github.josephsimutis.chess.BoardState
import io.github.josephsimutis.chess.Timeline
import io.github.josephsimutis.chess.pieces.*
import io.github.josephsimutis.chess.toNotation

open class StandardMove(index: Int, startFile: Int, startRank: Int, val endFile: Int, val endRank: Int) : Move(index, startFile, startRank) {
    override val inBounds: Boolean
        get() = (1..8).contains(startFile) &&
                (1..8).contains(startRank) &&
                (1..8).contains(endFile) &&
                (1..8).contains(endRank)

    override fun toString() = "${toNotation(startFile, startRank)} -> ${toNotation(endFile, endRank)}"

    override fun toNotation(timeline: Timeline): String {
        timeline[index].also { board ->
            board[startFile, startRank].also { piece ->
                val letter = when (piece?.javaClass) {
                    King::class.java -> "K"
                    Queen::class.java -> "Q"
                    Rook::class.java -> "R"
                    Bishop::class.java -> "B"
                    Knight::class.java -> "N"
                    Pawn::class.java -> ""
                    else -> null
                }

                return "$letter${if (board[endFile, endRank] == null) "" else "${if (letter == "")toNotation(startFile, startRank)[0] else ""}x"}${toNotation(endFile, endRank)}"
            }
        }
    }

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
                Pair(this, board.copyOf().also { copy -> makeChanges(copy, piece) }).apply {
                    if (index == timeline.history.lastIndex) timeline.history += this
                    else timeline.history[index + 1] = this
                }
            }
        }
    }

    open fun makeChanges(board: BoardState, piece: Piece?) {
        board[endFile, endRank] = piece
        board[endFile, endRank]?.move()
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