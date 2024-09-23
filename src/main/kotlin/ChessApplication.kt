package io.github.josephsimutis

import io.github.josephsimutis.chess.BoardState
import io.github.josephsimutis.chess.Timeline
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.stage.Stage
import java.io.FileInputStream

class ChessApplication : Application() {
    override fun start(stage: Stage) {
        // TODO: Allow user to enter this path manually.
        val pieceSpritePath = "/home/josephsimutis/Documents/test-chess-sprites/"

        val game = Timeline(ArrayList())
        game += BoardState.START

        var currentBoard = game.boards.lastIndex

        val grid = GridPane()
        for (x in 0..7) {
            for (y in 0..7) {
                val squareBackground = Rectangle()
                if ((x + y) % 2 == 0) squareBackground.fill = Color.LIGHTBLUE
                else squareBackground.fill = Color.BLUE
                squareBackground.widthProperty().bind(grid.widthProperty().divide(8))
                squareBackground.heightProperty().bind(grid.heightProperty().divide(8))

                val square = StackPane()
                square.children.add(squareBackground)

                val piece = game.boards[currentBoard][x + 1, 8 - y]
                if (piece != null) {
                    if (piece.piece != null && piece.side != null) {
                        square.children.add(ImageView(Image(FileInputStream("$pieceSpritePath${piece.side.sideName}_${piece.piece.pieceName}.png"))))
                    }
                }
                grid.add(square, x, y)
            }
        }
        val scene = Scene(grid, 512.0, 512.0)

        stage.title = "Chess with Linear Time Travel"
        stage.scene = scene
        stage.isResizable = false
        stage.show()
    }
}