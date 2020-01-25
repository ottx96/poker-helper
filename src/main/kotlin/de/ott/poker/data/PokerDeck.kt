package de.ott.poker.data

import java.util.*

object PokerDeck {

    private var _DECK = createDeck()
    val DECK = _DECK

    fun reset() {
        _DECK = createDeck()
    }


    private fun createDeck(): LinkedList<PokerCard> {
        val res = LinkedList<PokerCard>()

        for(number in 2..14)
            for(color in 0..3)
                res.add( PokerCard(Numbers.byId(number), Colors.byId(color), PokerCardInformation()) )

        return res
    }

    fun getCardbyAttributes(color: Colors, number: Numbers) =
        DECK.first { it.color == color && it.number == number }

}