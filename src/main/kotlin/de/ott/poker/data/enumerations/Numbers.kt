package de.ott.poker.data.enumerations

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