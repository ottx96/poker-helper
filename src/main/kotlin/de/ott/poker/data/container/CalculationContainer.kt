package de.ott.poker.data.container

import de.ott.poker.data.enumerations.PokerHand

data class CalculationContainer(val hand: PokerHand = PokerHand.HIGH_CARD, var probability: Double = 0.4, val desc: String = hand.desc)