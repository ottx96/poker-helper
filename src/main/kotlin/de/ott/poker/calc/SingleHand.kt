package de.ott.poker.calc

import de.ott.poker.data.PokerCard
import de.ott.poker.data.enumerations.Color
import de.ott.poker.data.enumerations.Numbers
import de.ott.poker.data.enumerations.PokerHand
import java.util.*

object SingleHand {

    fun highCard(cards: List<PokerCard>): Boolean = true

    fun pair(cards: List<PokerCard>): Boolean {
        cards.forEach { cCard ->
            cards.filter { it != cCard }.forEach {
                if(cCard.number == it.number) return true
            }
        }
        return false
    }

    fun twoPair(cards: List<PokerCard>): Boolean {
        var count = 0
        val l: LinkedList<Numbers> = LinkedList()
        cards.forEach{ c1 ->
            cards.filter { c1 != it}.forEach {c2 ->
                if(c1.number == c2.number && l.none { it == c2.number }){
                    count++
                    l.add(c1.number)
                }
            }
        }
        return count >= 2
    }

    fun threeOfAKind(cards: List<PokerCard>): Boolean {
        cards.forEach { cCard ->
            var count = 1
            cards.filter { it != cCard }.forEach {
                if (cCard.number == it.number)
                    count++
            }
            if (count >= 3) return true
        }
        return false
    }

    fun straight(cards: List<PokerCard>): Boolean {
        val sortedList = cards.sortedBy { it.number.id }.toMutableList()
        val removedCards = mutableListOf<PokerCard>()

        var i = 0
        while (i < sortedList.size - 1){
            if (sortedList[i].number.id == sortedList[i + 1].number.id)
                removedCards.add(sortedList[++i])
            i++
        }
        removedCards.forEach { sortedList.remove(it) }

        repeat(sortedList.size - 4){
            val subList = sortedList.subList(it, it + 5)
            var lastCard = subList.first()
            var passed = true
            for(pc in subList){
                if(pc.number.id - lastCard.number.id > 1){
                    passed = false
                    break
                }
                lastCard = pc
            }
            if(passed) return true
        }
        return false
    }

    fun flush(cards: List<PokerCard>): Boolean {
        Color.values().forEach { col ->
            if(cards.count { it.color == col } >= 5) return true
        }
        return false
    }

    fun fullHouse(cards: List<PokerCard>): Boolean {
        var triple: Numbers? = null
        cards.forEach {cCard ->
            if(cards.count { it.number == cCard.number } >= 3){
                triple = cCard.number
            }
        }
        if(triple == null) return false
        cards.filter { it.number != triple }.forEach { cCard ->
            if(cards.count { it.number == cCard.number } >= 2) return true
        }
        return false
    }

    fun fourOfAKind(cards: List<PokerCard>): Boolean {
        cards.forEach { cCard ->
            var count = 1
            cards.filter { it != cCard }.forEach {
                if (cCard.number == it.number) count++
            }
            if (count >= 4) return true
        }
        return false
    }

    fun straightFlush(cards: List<PokerCard>): Boolean {
        val sortedList = cards.sortedBy { it.number.id }.toMutableList()
        val removedCards = mutableListOf<PokerCard>()

        var i = 0
        while (i < sortedList.size - 1){
            if (sortedList[i].number.id == sortedList[i + 1].number.id)
                removedCards.add(sortedList[++i])
            i++
        }
        removedCards.forEach { sortedList.remove(it) }

        repeat(sortedList.size - 4){
            val subList = sortedList.subList(it, it + 5)
            var lastCard = subList.first()
            var passed = true
            for(pc in subList){
                if(pc.number.id - lastCard.number.id > 1){
                    passed = false
                    break
                }
                if(pc.color != lastCard.color && pc.color != removedCards.firstOrNull { it.number.id == pc.number.id }?.color?:false){
                    passed = false
                    break
                }
                lastCard = pc
            }
            if(passed) return true
        }
        return false
    }

    fun getHighestHand(hand: List<PokerCard>, table: List<PokerCard>): PokerHand {
        return getHighestHand(hand.toMutableList().apply {
            addAll(table)
        })
    }

    fun getHighestHand(handLeft: PokerCard, handRight: PokerCard, table: List<PokerCard>): PokerHand {
        return getHighestHand(table.toMutableList().apply {
            add(handLeft)
            add(handRight)
        })
    }

    fun getHighestHand(cards: List<PokerCard>) =
            PokerHand.values().toList().sortedBy { - it.weight }.first { it.applies(cards) }

    fun getHands(hand: List<PokerCard>, table: List<PokerCard>): List<PokerHand> {
        return getHands(hand.toMutableList().apply {
            addAll(table)
        })
    }

    fun getHands(handLeft: PokerCard, handRight: PokerCard, table: List<PokerCard>): List<PokerHand> {
        return getHands(table.toMutableList().apply {
            add(handLeft)
            add(handRight)
        })
    }

    fun getHands(cards: List<PokerCard>): List<PokerHand> =
        PokerHand.values().toList()
                .sortedBy{ - it.weight }
                .filter{ it.applies(cards) }

}