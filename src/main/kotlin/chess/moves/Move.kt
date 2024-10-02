package io.github.josephsimutis.chess.moves

import io.github.josephsimutis.chess.Timeline

abstract class Move(val index: Int, val startFile: Int, val startRank: Int) {
    open val inBounds: Boolean
        get() = (1..8).contains(startFile) &&
                (1..8).contains(startRank)

    abstract fun toNotation(timeline: Timeline): String

    abstract fun applyTo(timeline: Timeline)
}