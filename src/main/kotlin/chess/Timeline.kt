package io.github.josephsimutis.chess

data class Timeline(val boards: ArrayList<BoardState>) {
    operator fun plusAssign(board: BoardState) {
        boards += board
    }

    fun getActiveSide(index: Int) = if (index >= boards.size || index < 0) null else if (index % 2 == 0) Side.DARK else Side.LIGHT

    fun getValidMoves(index: Int): Array<BoardState>? {
        val board = boards.getOrElse(index) { return null }
        TODO("Complete move generation")
    }

    fun isValidMove(index: Int, move: BoardState) = getValidMoves(index)?.contains(move)
}