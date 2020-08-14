package de.ott.poker.calc.task

import de.ott.poker.data.PokerCard
import de.ott.poker.data.enumerations.PokerHand
import javafx.concurrent.Task

class CurrentHandTask(val cards: List<PokerCard>): Task<PokerHand>() {

    override fun call(): PokerHand {
        return PokerHand.getHighest(cards)
    }

}