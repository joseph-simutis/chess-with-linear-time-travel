package io.github.josephsimutis.display

import io.github.josephsimutis.chess.Timeline
import io.github.josephsimutis.chess.moves.StandardMove
import javafx.application.Application
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.scene.text.TextAlignment
import javafx.stage.Stage

class ChessApplication : Application() {
    // Todo: allow user to add path manually
    private val pieceSpritePath = "/home/josephsimutis/Documents/test-chess-sprites/"
    private val game = Timeline()
    private var currentBoard = -1

    override fun start(stage: Stage) {
        stage.title = "Chess with Linear Time Travel"
        stage.scene = Scene(HBox().also { hBox -> hBox.children.addAll(
            GridPane().apply {
                prefWidth = BOARD_WIDTH
                prefHeight = BOARD_HEIGHT
                redrawBoard(this)
            },
            VBox().also { vBox ->
                vBox.prefWidth = GAME_WIDTH - BOARD_WIDTH
                vBox.prefHeight = GAME_HEIGHT
                vBox.alignment = Pos.CENTER
                vBox.children.addAll(Text("Timeline View").apply {
                    font = Font.font("verdana", 30.0)
                    textAlignment = TextAlignment.CENTER
                }, Text(currentBoard.toString()))
            }
        ) }, GAME_WIDTH, 512.0).apply {
            addEventHandler(KeyEvent.KEY_PRESSED) { key ->
                when (key.code) {
                    /*KeyCode.LEFT -> {
                        if (currentBoard >= 0) {
                            currentBoard--
                            println(currentBoard)
                            redrawBoard(root.childrenUnmodifiable[0] as GridPane)
                        }
                    }
                    KeyCode.RIGHT -> {
                        if (currentBoard < game.history.lastIndex) {
                            currentBoard++
                            println(currentBoard)
                            redrawBoard(root.childrenUnmodifiable[0] as GridPane)
                        }
                    }*/
                    else -> {}
                }
            }
        }
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
                            stack.children.add(PieceView(grid, { endX, endY ->
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

    companion object {
        val GAME_WIDTH = 828.416
        val GAME_HEIGHT = 512.0
        val BOARD_WIDTH = 512.0
        val BOARD_HEIGHT = 512.0
    }
}