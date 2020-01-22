package de.ott.poker.data.calc

import de.ott.poker.data.Colors
import de.ott.poker.data.Numbers
import de.ott.poker.data.PokerCard
import java.util.*

class SingleHandCalc(val first: PokerCard, val second: PokerCard, val tableCards: List<PokerCard>) {

    val allCards = LinkedList<PokerCard>()

    init {
        allCards.add(first)
        allCards.add(second)
        allCards.addAll(tableCards)
    }

    fun pair(): Boolean {
        allCards.forEach { cCard ->
            allCards.filter { it != cCard }.forEach {
                if(cCard.number == it.number) return true
            }
        }
        return false
    }

    fun twoPair(): Boolean {
        allCards.forEach { cCard ->
            var count = 0
            allCards.filter { it != cCard }.forEach {
                if (cCard.number == it.number) count++
                if(count >= 2) return true
            }
        }
        return false
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
        val sorted = allCards.sortedBy { it.number.id }
        for(i in 0..2){
            for(j in i..i+4){
                if(sorted[j].number.id != sorted[j+1].number.id) break
                else if(j == i+4) return true
            }
        }
        return false
    }

    fun flush(): Boolean {
        Colors.values().forEach {col ->
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
        return flush() && straight()
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