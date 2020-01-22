package de.ott.poker.data

enum class Colors(val id: Int, val desc: String) {
    KARO(0, "Karo"), PIK(1, "Pik"), HERZ(2, "Herz"), KREUZ(3, "Kreuz");

    companion object {
        fun byId(id: Int): Colors =
            Colors.values().first { it.id == id }
    }

    override fun toString(): String {
        return desc
    }

}

enum class Numbers(val id: Int, val desc: String = id.toString()) {
    ACE(1, "Ass"),
    TWO(2), THREE(3), FOUR(4), FIVE(5),
    SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10),
    BUBE(11, "Bube"), DAME(12, "Dame"), KOENIG(13, "Koenig");

    companion object {
        fun byId(id: Int) =
            Numbers.values().first { it.id == id }
    }

    override fun toString(): String {
        return desc
    }
}