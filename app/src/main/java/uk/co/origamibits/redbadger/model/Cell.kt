package uk.co.origamibits.redbadger.model

sealed class Cell {
    object Empty : Cell()
    object ScentOfLostRobot : Cell()
}