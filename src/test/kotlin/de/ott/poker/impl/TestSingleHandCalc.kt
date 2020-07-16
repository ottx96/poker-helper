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
    fun `test straight (negative)`(){
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

        val calc = SingleHandCalc(hand[0], hand[1], table)
        assert(!calc.straight())
        assert(calc.pair())
        assert(calc.highCard())
        assert(calc.getHighest() != PokerHands.STRAIGHT)
        assert(calc.getHighest() == PokerHands.ONE_PAIR)
    }

}