package de.ott.poker.data

import java.util.*

object PokerDeck {

    val CARDS by lazy {
        val res = LinkedList<PokerCard>()

        for(number in 1..13)
            for(color in 0..3)
                res.add( PokerCard(Numbers.byId(number), Colors.byId(color)) )

        return@lazy res
    }

}