package de.ott.poker.data

enum class Colors(val id: Int, val desc: String) {
    KARO(0, "D"), PIK(1, "S"), HERZ(2, "H"), KREUZ(3, "C");

    companion object {
        fun byId(id: Int): Colors =
            Colors.values().first { it.id == id }

        fun byName(name: String) =
            Colors.values().first { it.desc.compareTo(name, true) == 0 }
    }

    override fun toString(): String {
        return desc
    }

}

enum class Numbers(val id: Int, val desc: String = id.toString()) {
    TWO(2), THREE(3), FOUR(4), FIVE(5),
    SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10),
    BUBE(11, "J"), DAME(12, "Q"), KOENIG(13, "K"),
    ACE(14, "A");

    companion object {
        fun byId(id: Int) =
            Numbers.values().first { it.id == id }

        fun byName(name: String) =
            Numbers.values().first { it.desc.startsWith(name) }

        fun byName(name: Int) =
            Numbers.values().first { it.desc.compareTo(name.toString(), true) == 0 }
    }

    override fun toString(): String {
        return desc
    }
}

enum class PokerHands(val desc: String, val weight: Int, private val function: (List<PokerCard>) -> Boolean){
    STRAIGHT_FLUSH("Straight Flush", 8, SingleHand::straightFlush),
    FOUR_OF_A_KIND("Vierling", 7, SingleHand::fourOfAKind),
    FULL_HOUSE("Full House", 6, SingleHand::fullHouse),
    FLUSH("Flush", 5, SingleHand::flush),
    STRAIGHT("Straight", 4, SingleHand::straight),
    THREE_OF_A_KIND("Drilling", 3, SingleHand::threeOfAKind),
    TWO_PAIR("Doppelpaar", 2, SingleHand::twoPair),
    ONE_PAIR("Paar", 1, SingleHand::pair),
    HIGH_CARD("Höchste Karte", 0, SingleHand::highCard);

    companion object{
        fun getHighest(cards: List<PokerCard>): PokerHands {
            return SingleHand.getHighestHand(cards)
        }

        fun getHands(cards: List<PokerCard>): List<PokerHands> {
            return SingleHand.getHands(cards)
        }
    }


    fun applies(cards: List<PokerCard>) = function.invoke(cards)

    override fun toString() = desc
}