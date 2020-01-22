package de.ott.poker.data

data class PokerCard(val number: Numbers, val color: Colors, val information: PokerCardInformation) {
    override fun toString(): String {
        return """$color $number"""// [$information]""".trimMargin()
    }
}