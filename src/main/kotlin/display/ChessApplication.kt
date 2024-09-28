package io.github.josephsimutis.display

import io.github.josephsimutis.chess.Timeline
import io.github.josephsimutis.chess.moves.StandardMove
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.GridPane
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.stage.Stage

class ChessApplication : Application() {
    // Todo: allow user to add path manually
    private val pieceSpritePath = "/home/josephsimutis/Documents/test-chess-sprites/"
    private val game = Timeline()
    private var currentBoard = -1

    override fun start(stage: Stage) {
        stage.title = "Chess with Linear Time Travel"
        stage.scene = Scene(GridPane().also { grid -> redrawBoard(grid) }, 512.0, 512.0)
        stage.isResizable = false
        stage.show()
    }

    private fun redrawBoard(grid: GridPane) {
        game[currentBoard].also { board ->
            grid.children.clear()
            for (x in 0..7) {
                for (y in 0..7) {
                    grid.add(StackPane().also { stack ->
                        stack.children.add(Rectangle().apply {
                            fill = if ((x + y) % 2 == 0) Color.LIGHTBLUE else Color.BLUE
                            widthProperty().bind(grid.widthProperty().divide(8))
                            heightProperty().bind(grid.heightProperty().divide(8))
                        })
                        board[x + 1, 8 - y]?.apply {
                            stack.children.add(PieceView({ endX, endY ->
                                val moved = game.attemptMove(StandardMove(currentBoard, x + 1, 8 - y, endX, 9 - endY))
                                if (game.history.lastIndex - 1 == currentBoard) currentBoard++
                                if (moved) redrawBoard(grid)
                            }, this, pieceSpritePath))
                        }
                    }, x, y)
                }
            }
        }
    }
}