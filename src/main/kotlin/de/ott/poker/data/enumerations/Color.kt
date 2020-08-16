package de.ott.poker.data.enumerations

enum class Color(val id: Int, val desc: String, val imageId: String) {
    CHECK(0, "♣", "C"), PIK(1, "♠", "S"), HEART(2, "❤", "H"), CROSS(3, "♦", "D");

    companion object {
        fun byId(id: Int): Color =
                values().first { it.id == id }

        fun byName(name: String) =
                values().first { it.desc.compareTo(name, true) == 0 }
    }

    override fun toString(): String {
        return desc
    }

}