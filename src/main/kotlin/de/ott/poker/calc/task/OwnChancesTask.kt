package de.ott.poker.calc.task

import de.ott.poker.calc.Calculations
import de.ott.poker.data.PokerCard
import de.ott.poker.data.PokerDeck
import de.ott.poker.data.container.CalculationContainer
import de.ott.poker.data.enumerations.PokerHand
import javafx.concurrent.Task
import java.lang.Exception

class OwnChancesTask(val container: CalculationContainer = CalculationContainer(), private val cards: List<PokerCard>): Task<CalculationContainer>() {

    internal fun testCall() = call()

    override fun call(): CalculationContainer {
        val deck = PokerDeck.createParallelDeck()
        deck.removeAll(cards)

        container.probability = -1.0

        if( cards.size == 7 ) {
            if(container.hand == PokerHand.getHighest(cards))
                container.probability = 1.0
            else container.probability = 0.0
        }

        var count = 0.0
        var matches = 0.0
        when (cards.size) {
            6 -> {
                deck.forEach {
                    val allCards = mutableListOf(it).apply { addAll(cards) }
                    if(container.hand.applies(allCards))
                        matches++

                    count ++
                }
            }
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
            2 -> {
                deck.forEach { c1 ->
                    deck.filter { it != c1 }.forEach { c2 ->
                        deck.filter { it != c1 && it != c2 }.forEach { c3 ->
                            deck.filter { !listOf(c1, c2, c3).contains(it) }.forEach { c4 ->
                                deck.filter { !listOf(c1, c2, c3 ,c4).contains(it) }.forEach { c5 ->
                                    val allCards = mutableListOf(c1, c2, c3, c4, c5).apply { addAll(cards) }
                                    if(container.hand.applies(allCards))
                                        matches++

                                    count ++
                                }
                            }
                        }
                    }
                }
            }
            1 -> {
                deck.forEach { c1 ->
                    deck.filter { it != c1 }.forEach { c2 ->
                        deck.filter { it != c1 && it != c2 }.forEach { c3 ->
                            deck.filter { !listOf(c1, c2, c3).contains(it) }.forEach { c4 ->
                                deck.filter { !listOf(c1, c2, c3 ,c4).contains(it) }.forEach { c5 ->
                                    deck.filter { !listOf(c1,c2,c3,c4,c5).contains(it) }.forEach { c6 ->
                                        val allCards = mutableListOf(c1, c2, c3, c4, c5, c6).apply { addAll(cards) }
                                        if(container.hand.applies(allCards))
                                            matches++

                                        count ++
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        container.probability = matches / count

        Calculations.ownChancesTable?.refresh()
        return container
    }

}