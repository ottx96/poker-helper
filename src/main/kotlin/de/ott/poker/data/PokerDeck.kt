package de.ott.poker.data

import de.ott.poker.data.enumerations.Color
import de.ott.poker.data.enumerations.Numbers
import java.util.*

object PokerDeck {

    var DECK = createDeck()

    fun reset(): LinkedList<PokerCard> {
        DECK = createDeck()
        return DECK
    }

    fun createParallelDeck() = createDeck()

    private fun createDeck(): LinkedList<PokerCard> {
        val res = LinkedList<PokerCard>()

        for(number in 2..14)
            for(color in 0..3)
                res.add( PokerCard (Numbers.byId(number), Color.byId(color), PokerCardInformation()) )

        return res
    }

    fun getCardbyAttributes(color: Color, number: Numbers) =
        DECK.first { it.color == color && it.number == number }

}