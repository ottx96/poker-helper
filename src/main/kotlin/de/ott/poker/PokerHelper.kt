package de.ott.poker

import de.ott.poker.data.PokerDeck

object PokerHelper {

    @JvmStatic
    fun main(args: Array<String>) {
        PokerDeck.CARDS.forEach( ::println )
    }

}