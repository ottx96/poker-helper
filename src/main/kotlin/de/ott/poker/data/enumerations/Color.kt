package de.ott.poker.data.enumerations

enum class Color(val id: Int, val desc: String) {
    KARO(0, "D"), PIK(1, "S"), HERZ(2, "H"), KREUZ(3, "C");

    companion object {
        fun byId(id: Int): Color =
                Color.values().first { it.id == id }

        fun byName(name: String) =
                Color.values().first { it.desc.compareTo(name, true) == 0 }
    }

    override fun toString(): String {
        return desc
    }

}