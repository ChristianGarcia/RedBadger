package uk.co.origamibits.redbadger.model

sealed class Cell {
    object Empty : Cell()
    object ScentOfLostRobot : Cell()
    data class Robot(val orientation: Orientation) : Cell()
}