package io.github.josephsimutis.display

import io.github.josephsimutis.chess.BoardState
import io.github.josephsimutis.chess.Square
import io.github.josephsimutis.chess.Timeline
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.GridPane
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.stage.Stage
import kotlin.system.exitProcess

class ChessApplication : Application() {
    // Todo: allow user to add path manually
    private val pieceSpritePath = "/home/josephsimutis/Documents/test-chess-sprites/"
    private val game = Timeline(true)
    private var currentBoard = 0

    override fun start(stage: Stage) {
        game += BoardState.START
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
                game.boards[currentBoard][x + 1, 8 - y].also { square ->
                    if (square != null) {
                        if (square.piece != null && square.side != null) {
                            (grid.children[(x * 8) + y] as StackPane).also { stack ->
                                if (stack.children.size > 1) stack.children.removeAt(1)
                                stack.children.add(PieceView({ startX, startY, endX, endY ->
                                    game.attemptMove(
                                        currentBoard,
                                        game.boards[currentBoard]
                                            .with(startX, 9 - startY, square)?.with(endX - 8, endY - 8, Square.NONE)
                                            ?: return@PieceView
                                    )
                                    redrawPieces(grid)
                                }, square, pieceSpritePath))
                            }
                        }
                    }
                }
            }
        }
    }
}