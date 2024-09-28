package io.github.josephsimutis.display

import io.github.josephsimutis.chess.pieces.Piece
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane
import java.io.FileInputStream

class PieceView(val grid: GridPane, val attemptMove: (Int, Int) -> Unit, piece: Piece, pieceSpritePath: String) : ImageView(Image(FileInputStream("$pieceSpritePath${piece.side.sideName}_${piece.pieceName}.png"))) {
    private var anchorX = 0.0
    private var anchorY = 0.0

    init {
        setOnMousePressed { event ->
            anchorX = event.screenX
            anchorY = event.screenY
            parent.viewOrder = -1.0
        }
        setOnMouseDragged { event ->
            translateX = event.screenX - anchorX
            translateY = event.screenY - anchorY
        }
        setOnMouseReleased { event ->
            translateX = 0.0
            translateY = 0.0
            parent.viewOrder = 0.0
            val (gridX, gridY) = grid.screenToLocal(event.screenX, event.screenY).let { Pair(it.x, it.y) }
            attemptMove(
                ((gridX / grid.width) * 8).toInt() + 1,
                ((gridY / grid.height) * 8).toInt() + 1
            )
        }
    }
}