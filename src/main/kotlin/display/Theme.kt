package io.github.josephsimutis.display

import javafx.scene.paint.Color

data class Theme(val lightSquare: Color, val darkSquare: Color, val lightSquareHighlighted: Color, val darkSquareHighlighted: Color) {
    fun light(highlighted: Boolean): Color = if (highlighted) lightSquareHighlighted else lightSquare

    fun dark(highlighted: Boolean): Color = if (highlighted) darkSquareHighlighted else darkSquare

    companion object {
        val DEFAULT = Theme(Color.LIGHTBLUE, Color.BLUE, Color.GREENYELLOW, Color.GREEN)
    }
}