package io.github.josephsimutis.chess

data class Timeline(val boards: ArrayList<BoardState>) {
    constructor(startingPosition: Boolean) : this(ArrayList()) {
        if (startingPosition) this += BoardState.START
    }

    operator fun plusAssign(board: BoardState) {
        boards += board
    }

    fun getActiveSide(index: Int) = if (index >= boards.size || index < 0) null else if (index % 2 == 0) Side.DARK else Side.LIGHT

    fun getValidMoves(index: Int): Array<BoardState>? {
        val board = boards.getOrElse(index) { return null }
        TODO("Complete move generation")
    }

    fun isValidMove(index: Int, move: BoardState) = getValidMoves(index)?.contains(move)

    fun attemptMove(index: Int, move: BoardState) {
        //if (isValidMove(index, move) == true)
            move(index, move)
    }

    fun move(index: Int, move: BoardState) {
        if (index == boards.lastIndex) boards += move
        else boards[index] = move
    }
}