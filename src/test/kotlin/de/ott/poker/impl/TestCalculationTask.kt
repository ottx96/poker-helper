package de.ott.poker.impl

import de.ott.poker.data.*
import de.ott.poker.data.enumerations.Color
import de.ott.poker.data.enumerations.Numbers
import de.ott.poker.data.enumerations.PokerHand
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TestCalculationTask {

    private var deck = PokerDeck.reset()

    @BeforeEach
    fun initDeck(){
        deck = PokerDeck.reset()
    }

    @Test
    fun `test single hand calc`(){
        val hand =
            listOf(
                PokerCard(Numbers.TWO, Color.PIK),
                PokerCard(Numbers.THREE, Color.PIK)
            )

        val table =
            listOf(
                PokerCard(Numbers.FOUR, Color.PIK),
                PokerCard(Numbers.FIVE, Color.PIK),
                PokerCard(Numbers.FIVE, Color.KARO)
            )

        val cards = hand.toMutableList().apply {
            addAll(table)
        }

        assert(PokerHand.HIGH_CARD.applies(cards))
        assert(PokerHand.ONE_PAIR.applies(cards))
        assert(!PokerHand.STRAIGHT.applies(cards))
        assert(PokerHand.getHighest(cards) == PokerHand.ONE_PAIR)
        assert(PokerHand.getHighest(cards) != PokerHand.STRAIGHT)
    }

}