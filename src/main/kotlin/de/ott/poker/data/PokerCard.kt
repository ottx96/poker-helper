package de.ott.poker.data

import de.ott.poker.data.enumerations.Color
import de.ott.poker.data.enumerations.Numbers

data class PokerCard(val number: Numbers, val color: Color, var information: PokerCardInformation = PokerCardInformation()) {
    override fun toString(): String {
        return """$color $number"""// [$information]""".trimMargin()
    }

    fun getImageURL(): String{
        return """de.ott.poker.cards/${number.desc}${color.imageId}.png"""
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PokerCard

        if (number != other.number) return false
        if (color != other.color) return false

        return true
    }

    override fun hashCode(): Int {
        var result = number.hashCode()
        result = 31 * result + color.hashCode()
        return result
    }
}