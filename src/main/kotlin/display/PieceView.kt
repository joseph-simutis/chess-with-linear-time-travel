package io.github.josephsimutis.display

import io.github.josephsimutis.chess.Square
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import java.io.FileInputStream
import kotlin.math.floor

class PieceView(val attemptMove: (Int, Int, Int, Int) -> Unit, piece: Square, pieceSpritePath: String) : ImageView(Image(FileInputStream("$pieceSpritePath${piece.side!!.sideName}_${piece.piece!!.pieceName}.png"))) {
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
            println(
                floor(event.sceneX / (scene.width / 8)).toInt().toString() + ", " + floor(event.sceneY / (scene.height / 8)).toInt().toString()
            )
            println(
                floor(anchorX / (scene.width / 8)).toInt().toString() + ", " + floor(anchorY / (scene.height / 8)).toInt().toString()
            )
            attemptMove(
                floor(event.sceneX / (scene.width / 8)).toInt() + 1,
                floor(event.sceneY / (scene.height / 8)).toInt() + 1,
                floor(anchorX / (scene.width / 8)).toInt() + 1,
                floor(anchorY / (scene.height / 8)).toInt() + 1
            )
        }
    }
}