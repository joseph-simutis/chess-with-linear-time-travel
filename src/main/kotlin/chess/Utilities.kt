package io.github.josephsimutis.chess

fun toNotation(file: Int, rank: Int): String? {
    if (!(1..8).contains(file) || !(1..8).contains(rank)) return null
    return "${Char(file + 96)}$rank"
}

fun fromNotation(notation: String): Pair<Int, Int>? {
    if (notation.length != 2) return null
    if (!(97..104).contains(notation[0].code)) return null
    if (!(49..56).contains(notation[1].code)) return null
    return Pair(notation[0].code - 96, notation[1].code - 48)
}