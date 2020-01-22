package de.ott.poker.data

data class PokerCard(val number: Numbers, val color: Colors) {
    override fun toString(): String {
        return """$color $number"""
    }
}