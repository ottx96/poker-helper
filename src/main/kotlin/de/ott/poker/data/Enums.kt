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