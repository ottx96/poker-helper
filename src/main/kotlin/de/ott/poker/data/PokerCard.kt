package de.ott.poker.data

data class PokerCard(val number: Numbers, val color: Colors, var information: PokerCardInformation = PokerCardInformation()) {
    override fun toString(): String {
        return """$color $number"""// [$information]""".trimMargin()
    }

    fun getImageURL(): String{
        return """de.ott.poker.cards/${number.desc}${color.desc}.png"""
    }
}