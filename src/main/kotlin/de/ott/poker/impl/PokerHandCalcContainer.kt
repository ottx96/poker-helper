package de.ott.poker.impl

import de.ott.poker.data.enumerations.PokerHand

data class PokerHandCalcContainer(val hand: PokerHand = PokerHand.HIGH_CARD, var probability: Double = 0.4, val desc: String = hand.desc)