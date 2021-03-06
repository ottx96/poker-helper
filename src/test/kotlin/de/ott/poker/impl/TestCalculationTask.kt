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
                PokerCard(Numbers.FIVE, Color.CHECK)
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

    @Test
    fun `test straight flush`(){
        val cards = listOf(
            PokerCard(Numbers.TWO, Color.PIK),
            PokerCard(Numbers.THREE, Color.HEART),

            PokerCard(Numbers.TEN, Color.CHECK),
            PokerCard(Numbers.JACK, Color.CHECK),
            PokerCard(Numbers.QUEEN, Color.CHECK),
            PokerCard(Numbers.KING, Color.CHECK),
            PokerCard(Numbers.ACE, Color.CHECK)
        )

        assert(PokerHand.STRAIGHT.applies(cards))
        assert(PokerHand.FLUSH.applies(cards))
        assert(PokerHand.STRAIGHT_FLUSH.applies(cards))
    }

    @Test
    fun `test straight and flush but not straight flush`(){
        val cards = listOf(
            PokerCard(Numbers.TWO, Color.CHECK),
            PokerCard(Numbers.TEN, Color.PIK),
            PokerCard(Numbers.TEN, Color.HEART),
            PokerCard(Numbers.JACK, Color.CHECK),
            PokerCard(Numbers.QUEEN, Color.CHECK),
            PokerCard(Numbers.KING, Color.CHECK),
            PokerCard(Numbers.ACE, Color.CHECK)
        )

        assert(PokerHand.STRAIGHT.applies(cards))
        assert(PokerHand.FLUSH.applies(cards))
        assert(!PokerHand.STRAIGHT_FLUSH.applies(cards))
    }

}