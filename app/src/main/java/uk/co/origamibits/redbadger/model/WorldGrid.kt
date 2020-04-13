package uk.co.origamibits.redbadger.model

data class WorldGrid(val x: Int, val y: Int) {
    val grid = Array<Array<Cell>>(x + 1) {
        Array(y + 1) { Cell.Empty }
    }
}

