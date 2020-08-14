package de.ott.poker.impl

import de.ott.poker.data.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TestSingleHandCalc {

    var deck = PokerDeck.reset()

    @BeforeEach
    fun initDeck(){
        deck = PokerDeck.reset()
    }

    @Test
    fun `test single hand calc`(){
        val hand =
            listOf(
                PokerCard(Numbers.TWO, Colors.PIK),
                PokerCard(Numbers.THREE, Colors.PIK)
            )

        val table =
            listOf(
                PokerCard(Numbers.FOUR, Colors.PIK),
                PokerCard(Numbers.FIVE, Colors.PIK),
                PokerCard(Numbers.FIVE, Colors.KARO)
            )

        val cards = hand.toMutableList().apply {
            addAll(table)
        }

        assert(PokerHands.HIGH_CARD.applies(cards))
        assert(PokerHands.ONE_PAIR.applies(cards))
        assert(!PokerHands.STRAIGHT.applies(cards))
        assert(PokerHands.getHighest(cards) == PokerHands.ONE_PAIR)
        assert(PokerHands.getHighest(cards) != PokerHands.STRAIGHT)
    }

}