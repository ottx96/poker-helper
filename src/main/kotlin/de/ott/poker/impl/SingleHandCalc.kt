package de.ott.poker.impl

import de.ott.poker.data.Colors
import de.ott.poker.data.Numbers
import de.ott.poker.data.PokerCard
import de.ott.poker.data.PokerHands
import java.util.*

class SingleHandCalc(val first: PokerCard, val second: PokerCard, val tableCards: List<PokerCard>) {

    val allCards = LinkedList<PokerCard>()

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
        println(this)
    }

    fun getHighest(): String{
        NAME_TO_FUNCTION.forEach {
            if(it.value.invoke()) return it.key
        }
        return "Highest Card"
    }

    //TODO: Hier die Sortierte Liste der Karten liefern und null entspricht false!

    fun highCard(): Boolean = true

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
        for(offset in 0..sorted.size - 5){
           var straight = true
           for(i in 1..5){
               if(sorted[i+offset].number.id - 1 != sorted[i+offset].number.id){
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
        for(offset in 0..sorted.size - 5){
            var straight_flush = true
            for(i in 1..6){
                if(sorted[i+offset].number.id - 1 != sorted[i+offset].number.id || sorted[i+offset].color != sorted[i+offset].color){
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
            |${PokerHands.HIGH_CARD}			   ${highCard()}
            |${PokerHands.ONE_PAIR}			       ${pair()}
            |${PokerHands.TWO_PAIR}			       ${twoPair()}
            |${PokerHands.THREE_OF_A_KIND}			   ${threeOfAKind()}
            |${PokerHands.STRAIGHT}			       ${straight()}
            |${PokerHands.FLUSH}			       ${flush()}
            |${PokerHands.FULL_HOUSE}			   ${fullHouse()}
            |${PokerHands.FOUR_OF_A_KIND}			   ${fourOfAKind()}
            |${PokerHands.STRAIGHT_FLUSH}			   ${straightFlush()}
        """.trimMargin()
    }

}