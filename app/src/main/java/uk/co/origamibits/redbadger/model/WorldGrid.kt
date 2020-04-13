package uk.co.origamibits.redbadger.model

data class WorldGrid(val x: Int, val y: Int) {
    fun contains(position: Pair<Int, Int>): Boolean {
        val (x, y) = position
        if (x < 0 || y < 0) return false
        if (x > this.x) return false
        return y <= this.y
    }

    val grid = Array<Array<Cell>>(x + 1) {
        Array(y + 1) { Cell.Empty }
    }
}

