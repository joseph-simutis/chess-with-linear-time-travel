package io.github.josephsimutis.chess

fun toNotation(file: Int, rank: Int): String {
    return "${Char(file + 96)}$rank"
}

fun fromNotation(notation: String): Pair<Int, Int>? {
    if (notation.length != 2) return null
    return Pair(notation[0].code - 96, notation[1].code - 48)
}