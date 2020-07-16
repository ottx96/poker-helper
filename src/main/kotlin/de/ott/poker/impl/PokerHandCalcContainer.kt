package de.ott.poker.impl

import de.ott.poker.data.PokerHands

class PokerHandCalcContainer(val hand: PokerHands = PokerHands.HIGH_CARD, var probability: Double = 0.4, val desc: String = hand.desc)