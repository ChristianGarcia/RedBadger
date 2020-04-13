package uk.co.origamibits.redbadger.model

data class StartingPoint(
    val x: Int,
    val y: Int,
    val orientation: Orientation
)

enum class Orientation { N, S, E, W }
