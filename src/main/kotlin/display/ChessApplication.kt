package io.github.josephsimutis.display

import io.github.josephsimutis.chess.Timeline
import io.github.josephsimutis.chess.moves.StandardMove
import javafx.application.Application
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
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
        stage.scene = drawScene(stage)
        stage.isResizable = false
        stage.show()
    }

    private fun drawScene(stage: Stage): Scene =
        Scene(HBox().also { hBox ->
            game[currentBoard].also { board ->
                hBox.children.addAll(
                    GridPane().also { grid ->
                        grid.prefWidth = BOARD_WIDTH
                        grid.prefHeight = BOARD_HEIGHT
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
                                        stack.children.add(
                                            PieceView(
                                                grid,
                                                currentBoard == game.history.lastIndex && !board.gameOver,
                                                this,
                                                pieceSpritePath
                                            ) { endX, endY ->
                                                val moved = game.attemptMove(
                                                    StandardMove(
                                                        currentBoard,
                                                        x + 1,
                                                        8 - y,
                                                        endX,
                                                        9 - endY
                                                    )
                                                )
                                                if (game.history.lastIndex - 1 == currentBoard) currentBoard++
                                                if (moved) stage.scene = drawScene(stage)
                                            })
                                    }
                                }, x, y)
                            }
                        }
                    },
                    VBox().also { vBox ->
                        vBox.prefWidth = GAME_WIDTH - BOARD_WIDTH
                        vBox.prefHeight = GAME_HEIGHT
                        vBox.alignment = Pos.CENTER
                        vBox.children.addAll(
                            Text("The present is at move ${game.history.lastIndex + 2}"),
                            Text("You are at move ${currentBoard + 2}"),
                            (if (!game[currentBoard].gameOver) Text("${game.getActiveSide(currentBoard).sideName?.replaceFirstChar { char -> char.uppercase() }} to move")
                            else Text("${game[currentBoard].winner?.sideName?.replaceFirstChar { char -> char.uppercase() }} has won!")),
                            Text("Timeline View").apply {
                                font = Font.font("verdana", 24.0)
                                textAlignment = TextAlignment.CENTER
                            },
                            *game.history.mapIndexed { index, pair ->
                                Text(pair.first.toString()).apply {
                                    if (index == currentBoard) font = Font.font("verdana", FontWeight.BOLD, 12.0)
                                }
                            }.toTypedArray()
                        )
                    })
            }
        }, GAME_WIDTH, GAME_HEIGHT).apply {
            addEventHandler(KeyEvent.KEY_PRESSED) { key ->
                when (key.code) {
                    KeyCode.LEFT -> {
                        if (currentBoard >= 0) {
                            currentBoard--
                            stage.scene = drawScene(stage)
                        }
                    }

                    KeyCode.RIGHT -> {
                        if (currentBoard < game.history.lastIndex) {
                            currentBoard++
                            stage.scene = drawScene(stage)
                        }
                    }

                    else -> {}
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