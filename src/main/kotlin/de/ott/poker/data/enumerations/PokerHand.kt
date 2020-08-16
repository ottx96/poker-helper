package de.ott.poker.data.enumerations

import de.ott.poker.calc.SingleHand
import de.ott.poker.data.PokerCard
import de.ott.poker.international.Translator

enum class PokerHand(val desc: String, val weight: Int, private val function: (List<PokerCard>) -> Boolean){
    STRAIGHT_FLUSH(Translator.get("poker.hand.name.straight-flush"), 8, SingleHand::straightFlush),
    FOUR_OF_A_KIND(Translator.get("poker.hand.name.four-of-a-kind"), 7, SingleHand::fourOfAKind),
    FULL_HOUSE(Translator.get("poker.hand.name.full-house"), 6, SingleHand::fullHouse),
    FLUSH(Translator.get("poker.hand.name.flush"), 5, SingleHand::flush),
    STRAIGHT(Translator.get("poker.hand.name.straight"), 4, SingleHand::straight),
    THREE_OF_A_KIND(Translator.get("poker.hand.name.three-of-a-kind"), 3, SingleHand::threeOfAKind),
    TWO_PAIR(Translator.get("poker.hand.name.two-pair"), 2, SingleHand::twoPair),
    ONE_PAIR(Translator.get("poker.hand.name.one-pair"), 1, SingleHand::pair),
    HIGH_CARD(Translator.get("poker.hand.name.high-card"), 0, SingleHand::highCard);

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