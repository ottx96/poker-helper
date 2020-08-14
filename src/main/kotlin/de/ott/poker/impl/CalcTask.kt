package de.ott.poker.impl

import de.ott.poker.data.PokerCard
import de.ott.poker.data.PokerHands
import javafx.concurrent.Task

class CalcTask(val cards: List<PokerCard>): Task<PokerHands>() {

    override fun call(): PokerHands {
        return PokerHands.getHighest(cards)
    }

}