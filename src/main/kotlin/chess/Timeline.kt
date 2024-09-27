package io.github.josephsimutis.chess

data class Timeline(val history: ArrayList<Pair<Move, BoardState>>) {
    constructor() : this(ArrayList())

    operator fun get(index: Int): BoardState =
        if (history.isEmpty() || index < 0) BoardState.START
        else if (index > history.lastIndex) history.last().second
        else history[index].second

    private fun getActiveSide(index: Int) = if (index % 2 != 0) Side.LIGHT else Side.DARK

    fun attemptMove(index: Int, move: Move): Boolean {
        this[index]?.also { board ->
            if (board[move.startFile, move.startRank]?.side != getActiveSide(index)) return false
            if (board[move.startFile, move.startRank]
                    ?.getMoves(board, move.startFile, move.startRank)
                    ?.contains(move) != true
            ) return false
            move(index, move)
        }
        return true
    }

    fun move(index: Int, move: Move) {
        Pair(move, this[index - 1].with(move)).also { event ->
            if (history.isEmpty() || index == history.lastIndex) history += event
            else history[index] = event
            //println("The move history is: $history")
        }
    }
}