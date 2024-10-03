package io.github.josephsimutis.display

import io.github.josephsimutis.chess.Timeline
import io.github.josephsimutis.chess.moves.StandardMove
import io.github.josephsimutis.chess.toNotation
import javafx.application.Application
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.image.ImageView
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
import java.io.FileInputStream

class ChessApplication : Application() {
    // Todo: allow user to add path manually
    private val pieceSpritePath = "/home/josephsimutis/Documents/test-chess-sprites/"
    private val game = Timeline()
    private var currentBoard = -1
    private var selectedSquare: Triple<Int, Int, Int>? = null
    private var highlightedSquares: Array<Triple<Int, Int, Int>> = arrayOf()

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

                        if (game.discontinuousPoints.contains(currentBoard)) {
                            grid.setOnMousePressed { event ->
                                val square = grid.screenToLocal(event.screenX, event.screenY).let {
                                    Triple(
                                        currentBoard,
                                        ((it.x / grid.width) * 8).toInt() + 1,
                                        8 - ((it.y / grid.height) * 8).toInt()
                                    )
                                }
                                if (selectedSquare == null || game[square]?.side == game[selectedSquare!!]?.side) {
                                    highlightedSquares =
                                        game.getHighlightedSquares(square.first, square.second, square.third)
                                    selectedSquare = square
                                } else if (square == selectedSquare) {
                                    selectedSquare = null
                                    selectedSquare = square
                                } else {
                                    game.attemptMove(
                                        StandardMove(
                                            currentBoard,
                                            selectedSquare!!.second,
                                            selectedSquare!!.third,
                                            square.second,
                                            square.third
                                        )
                                    )
                                    if (game.history.lastIndex - 1 == currentBoard) currentBoard++
                                    selectedSquare = null
                                }
                                stage.scene = drawScene(stage)
                            }
                        }
                        for (x in 0..7) {
                            for (y in 0..7) {
                                grid.add(StackPane().also { stack ->
                                    stack.children.add(Rectangle().apply {
                                        val highlighted = isHighlighted(currentBoard, x + 1, 8 - y)
                                        fill =
                                            if ((x + y) % 2 == 0) theme.light(highlighted) else theme.dark(highlighted)
                                        widthProperty().bind(grid.widthProperty().divide(8))
                                        heightProperty().bind(grid.heightProperty().divide(8))
                                    })
                                    board[x + 1, 8 - y]?.apply {
                                        stack.children.add(ImageView(Image(FileInputStream("$pieceSpritePath${side.sideName}_${pieceName}.png"))))
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
                            (if (!board.gameOver) Text("${game.getActiveSide(currentBoard).sideName?.replaceFirstChar { char -> char.uppercase() }} to move")
                            else Text("${board.winner?.sideName?.replaceFirstChar { char -> char.uppercase() }} has won!")),
                            Text("Timeline View").apply {
                                font = Font.font("verdana", 24.0)
                                textAlignment = TextAlignment.CENTER
                            },
                            *game.history.mapIndexed { index, pair ->
                                Text("${index + 1}. ${pair.first.toNotation(game)}").apply {
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

    private fun isHighlighted(index: Int, file: Int, rank: Int) = highlightedSquares.contains(Triple(index, file, rank))

    companion object {
        const val GAME_WIDTH = 828.416
        const val GAME_HEIGHT = 512.0
        const val BOARD_WIDTH = 512.0
        const val BOARD_HEIGHT = 512.0

        val theme = Theme.DEFAULT
    }
}