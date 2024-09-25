package io.github.josephsimutis.chess

data class Timeline(val boards: ArrayList<BoardState>) {
    constructor(startingPosition: Boolean) : this(ArrayList()) {
        if (startingPosition) this += BoardState.START
    }

    operator fun plusAssign(board: BoardState) {
        boards += board
    }

    fun getActiveSide(index: Int) =
        if (index >= boards.size || index < 0) null else if (index % 2 == 0) Side.LIGHT else Side.DARK

    fun getValidMoves(index: Int): Array<BoardState>? {
        val moves = ArrayList<BoardState>()
        val board = boards.getOrElse(index) { return null }
        for (square in 0..63) {
            val file = square % 8
            val rank = square / 8
            val piece = board[file, rank]
            val side = getActiveSide(index)
            if (piece?.side == side) {
                moves.addAll(piece?.getMoves(board, file, rank) ?: return null)
            }
        }
        return moves.toTypedArray()
    }

    fun attemptMove(index: Int, move: BoardState): Boolean {
        if (getValidMoves(index)?.contains(move) == true) {
            move(index, move)
            return true
        }
        return false
    }


    fun move(index: Int, move: BoardState) {
        if (index == boards.lastIndex) boards += move else boards[index] = move
    }
}