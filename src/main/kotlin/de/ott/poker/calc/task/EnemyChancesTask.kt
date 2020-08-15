package de.ott.poker.calc.task

import de.ott.poker.calc.Calculations
import de.ott.poker.data.PokerCard
import de.ott.poker.data.PokerDeck
import de.ott.poker.data.container.CalculationContainer
import de.ott.poker.data.enumerations.PokerHand
import javafx.concurrent.Task

class EnemyChancesTask(val container: CalculationContainer = CalculationContainer(), private val cards: List<PokerCard>, private val handCards: List<PokerCard>): Task<CalculationContainer>() {

    override fun call(): CalculationContainer {
        val deck = PokerDeck.createParallelDeck()
        deck.removeAll(cards)
        deck.removeAll(handCards)

        container.probability = -1.0

        var count = 0.0
        var matches = 0.0

        // Nur, wenn die ersten 3 Karten liegen!
        when (cards.size) {
            5 -> {
                deck.forEach { c1 ->
                    deck.filter { it != c1 }.forEach { c2 ->
                        val allCards = mutableListOf(c1, c2).apply { addAll(cards) }
                        if(container.hand.applies(allCards))
                            matches++

                        count ++
                    }
                }
            }
            4 -> {
                deck.forEach { c1 ->
                    deck.filter { it != c1 }.forEach { c2 ->
                        deck.filter { it != c1 && it != c2 }.forEach { c3 ->
                            val allCards = mutableListOf(c1, c2, c3).apply { addAll(cards) }
                            if(container.hand.applies(allCards))
                                matches++

                            count ++
                        }
                    }
                }
            }
            3 -> {
                deck.forEach { c1 ->
                    deck.filter { it != c1 }.forEach { c2 ->
                        deck.filter { it != c1 && it != c2 }.forEach { c3 ->
                            deck.filter { !listOf(c1, c2, c3).contains(it) }.forEach { c4 ->
                                val allCards = mutableListOf(c1, c2, c3, c4).apply { addAll(cards) }
                                if(container.hand.applies(allCards))
                                    matches++

                                count ++
                            }
                        }
                    }
                }
            }
        }

        container.probability = matches / count

        Calculations.enemyChancesTable!!.refresh()
        return container
    }

}