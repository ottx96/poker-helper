package de.ott.poker.calc.task

import de.ott.poker.data.PokerCard
import de.ott.poker.data.PokerDeck
import de.ott.poker.data.enumerations.PokerHand
import javafx.concurrent.Task

class MaxHandTask(private val hand: List<PokerCard>, private val table: List<PokerCard>): Task<PokerHand>() {

    override fun call(): PokerHand {

        val deck = PokerDeck.createParallelDeck()
        deck.removeAll(hand)
        deck.removeAll(table)

        val placedCards = hand.toMutableList().apply { addAll(table) }

        var result = PokerHand.HIGH_CARD
        // Nur, wenn die ersten 3 Karten liegen
        if( table.size == 5 ) return PokerHand.getHighest( hand.toMutableList().apply { addAll(table) } )
        if( table.size == 4 ){
            deck.forEach {
                val currentCards = mutableListOf(it).apply { addAll(placedCards) }
                val curr = PokerHand.getHighest( currentCards )
                if(curr.weight > result.weight)
                    result = curr
            }
        }
        if(table.size == 3){
            deck.forEach { c1 ->
                deck.filter { it != c1 }.forEach { c2 ->
                    val currentCards = mutableListOf(c1, c2).apply { addAll(placedCards) }
                    val curr = PokerHand.getHighest( currentCards )
                    if(curr.weight > result.weight)
                        result = curr
                }
            }
        }

        return result
    }


}