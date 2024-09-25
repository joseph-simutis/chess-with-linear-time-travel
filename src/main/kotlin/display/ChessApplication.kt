package io.github.josephsimutis.display

import io.github.josephsimutis.chess.Timeline
import io.github.josephsimutis.chess.toNotation
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
    private val game = Timeline(true)
    private var currentBoard = 0

    override fun start(stage: Stage) {
        stage.title = "Chess with Linear Time Travel"
        stage.scene = Scene(GridPane().also { grid ->
            for (x in 0..7) {
                for (y in 0..7) {
                    grid.add(StackPane().also { stack ->
                        stack.children.add(Rectangle().apply {
                            fill = if ((x + y) % 2 == 0) Color.LIGHTBLUE else Color.BLUE
                            widthProperty().bind(grid.widthProperty().divide(8))
                            heightProperty().bind(grid.heightProperty().divide(8))
                        })
                    }, x, y)
                }
            }
            redrawPieces(grid)
        }, 512.0, 512.0)
        stage.isResizable = false
        stage.show()
    }

    private fun redrawPieces(grid: GridPane) {
        for (x in 0..7) {
            for (y in 0..7) {
                (grid.children[(x * 8) + y] as StackPane).also { stack ->
                    if (stack.children.size > 1) stack.children.removeAt(1)
                    game.boards[currentBoard][x + 1, 8 - y].also { piece ->
                        if (piece != null) {
                            stack.children.add(PieceView({ endX, endY ->
                                println("${toNotation(x + 1, 8 - y)} -> ${toNotation(endX, 9 - endY)}")
                                game.boards[currentBoard].moved(x + 1, 8 - y, endX, 9 - endY)?.let { newBoard ->
                                    game.attemptMove(
                                        currentBoard,
                                        newBoard
                                    )
                                }
                                redrawPieces(grid)
                            }, piece, pieceSpritePath))
                        }
                    }
                }
            }
        }
    }
}