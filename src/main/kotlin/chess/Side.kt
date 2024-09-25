package io.github.josephsimutis.chess

enum class Side(val sideName: String?, val direction: Int) {
    LIGHT("light", 1),
    DARK("dark", -1);

    operator fun not() = if (this == LIGHT) DARK else LIGHT
}