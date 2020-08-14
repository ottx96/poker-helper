package de.ott.poker.data.enumerations

import de.ott.poker.calc.SingleHand
import de.ott.poker.data.PokerCard

enum class PokerHand(val desc: String, val weight: Int, private val function: (List<PokerCard>) -> Boolean){
    STRAIGHT_FLUSH("Straight Flush", 8, SingleHand::straightFlush),
    FOUR_OF_A_KIND("Vierling", 7, SingleHand::fourOfAKind),
    FULL_HOUSE("Full House", 6, SingleHand::fullHouse),
    FLUSH("Flush", 5, SingleHand::flush),
    STRAIGHT("Straight", 4, SingleHand::straight),
    THREE_OF_A_KIND("Drilling", 3, SingleHand::threeOfAKind),
    TWO_PAIR("Doppelpaar", 2, SingleHand::twoPair),
    ONE_PAIR("Paar", 1, SingleHand::pair),
    HIGH_CARD("Höchste Karte", 0, SingleHand::highCard);

    companion object{
        fun getHighest(cards: List<PokerCard>): PokerHand {
            return SingleHand.getHighestHand(cards)
        }

        fun getHands(cards: List<PokerCard>): List<PokerHand> {
            return SingleHand.getHands(cards)
        }
    }


    fun applies(cards: List<PokerCard>) = function.invoke(cards)

    override fun toString() = desc
}