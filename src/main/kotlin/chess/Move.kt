package io.github.josephsimutis.chess

data class Move(val startFile: Int, val startRank: Int, val endFile: Int, val endRank: Int, var additionalEffects: (BoardState) -> Unit = {}) {
    val inBounds: Boolean
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Move

        if (startFile != other.startFile) return false
        if (startRank != other.startRank) return false
        if (endFile != other.endFile) return false
        if (endRank != other.endRank) return false

        return true
    }
}