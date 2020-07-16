package de.ott.poker.impl

import de.ott.poker.data.Colors
import de.ott.poker.data.Numbers
import de.ott.poker.data.PokerCard
import java.util.*

class SingleHandCalc(val first: PokerCard, val second: PokerCard, val tableCards: List<PokerCard>) {

    val allCards = LinkedList<PokerCard>()

    companion object {
        val NAMES = listOf("Straight Flush",
            "Vierling",
            "Full House",
            "Flush",
            "Straight",
            "Drilling",
            "Doppelpaar",
            "Paar",
            "Highest Card"
        )
    }

    val ORDER = mapOf(
        ::pair to 1,
        ::twoPair to 2,
        ::threeOfAKind to 3,
        ::straight to 4,
        ::flush to 5,
        ::fullHouse to 6,
        ::fourOfAKind to 7,
        ::straightFlush to 8
    )

    val NAME_TO_FUNCTION = mapOf("Straight Flush" to ::straightFlush,
    "Vierling" to ::fourOfAKind,
    "Full House" to ::fullHouse,
    "Flush" to ::flush,
    "Straight" to ::straight,
    "Drilling" to ::threeOfAKind,
    "Doppelpaar" to ::twoPair,
    "Paar" to ::pair)

    init {
        allCards.add(first)
        allCards.add(second)
        allCards.addAll(tableCards.filter { it != first && it != second })

        println("--SingleHandCalc--")
        allCards.forEach(::println)
    }

    fun getHighest(): String{
        NAME_TO_FUNCTION.forEach {
            if(it.value.invoke()) return it.key
        }
        return "Highest Card"
    }

    //TODO: Hier die Sortierte Liste der Karten liefern und null entspricht false!

    fun pair(): Boolean {
        allCards.forEach { cCard ->
            allCards.filter { it != cCard }.forEach {
                if(cCard.number == it.number) return true
            }
        }
        return false
    }

    fun twoPair(): Boolean {
        var count = 0
        val l: LinkedList<Numbers> = LinkedList()
        allCards.forEach{ c1 ->
            allCards.filter { c1 != it}.forEach {c2 ->
                if(c1.number == c2.number && l.none { it == c2.number }){
                    count++
                    l.add(c1.number)
                }
            }
        }
        return count >= 2
    }

    fun threeOfAKind(): Boolean {
        allCards.forEach { cCard ->
            var count = 1
            allCards.filter { it != cCard }.forEach {
                if (cCard.number == it.number) count++
            }
            if (count >= 3) return true
        }
        return false
    }

    fun straight(): Boolean {
        if(allCards.size < 5) return false
        val sorted = allCards.sortedBy { it.number.id }
        sorted.forEach(::println)
        for(offset in 0..sorted.size - 5){
           println("Offset: $offset")
           var straight = true
           for(i in 1..5){
               if(sorted[i+offset].number.id - 1 != sorted[i-1+offset].number.id){
                   straight = false
                   break
               }
               if(straight) return true
           }
        }
        return false
    }

    fun flush(): Boolean {
        Colors.values().forEach { col ->
            if(allCards.count { it.color == col } >= 5) return true
        }
        return false
    }

    fun fullHouse(): Boolean {
        var drilling: Numbers? = null
        allCards.forEach {cCard ->
            if(allCards.count { it.number == cCard.number } >= 3){
                drilling = cCard.number
            }
        }
        if(drilling == null) return false
        allCards.filter { it.number != drilling }.forEach {cCard ->
            if(allCards.count { it.number == cCard.number } >= 2) return true
        }
        return false
    }

    fun fourOfAKind(): Boolean {
        allCards.forEach { cCard ->
            var count = 1
            allCards.filter { it != cCard }.forEach {
                if (cCard.number == it.number) count++
            }
            if (count >= 4) return true
        }
        return false
    }

    fun straightFlush(): Boolean {
        if(allCards.size < 5) return false
        val sorted = allCards.sortedBy { it.number.id }
        sorted.forEach(::println)
        for(offset in 0..sorted.size - 5){
            println("Offset: $offset")
            var straight_flush = true
            for(i in 1..6){
                if(sorted[i+offset].number.id - 1 != sorted[i-1+offset].number.id || sorted[i+offset].color != sorted[i-1+offset].color){
                    straight_flush = false
                    break
                }
                if(straight_flush) return true
            }
        }
        return false
    }

    override fun toString(): String {
        return """------------------------------------------------
            ||$first | $second|
            |------------------------------------------------
            ||${tableCards.joinToString(" | ")}|
            |------------------------------------------------
            |Zwilling               ${pair()}
            |doppelter Zwilling     ${twoPair()}
            |Drilling               ${threeOfAKind()}
            |Straight (Strasse)     ${straight()}
            |Flush                  ${flush()}
            |Full House             ${fullHouse()}
            |Vierling               ${fourOfAKind()}
            |Straight Flush         ${straightFlush()}
        """.trimMargin()
    }

}