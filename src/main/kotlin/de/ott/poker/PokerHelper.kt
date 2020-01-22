package de.ott.poker

import de.ott.poker.data.Colors
import de.ott.poker.data.Numbers
import de.ott.poker.data.PokerDeck
import de.ott.poker.data.calc.SingleHandCalc
import java.util.*

object PokerHelper {

    val DECK = PokerDeck.DECK

    @JvmStatic
    fun main(args: Array<String>) {

        val FIRST  = PokerDeck.getCardbyAttributes(Colors.KREUZ, Numbers.KOENIG).apply { information.isHandCard = true }
        val SECOND = PokerDeck.getCardbyAttributes(Colors.KREUZ, Numbers.DAME).apply { information.isHandCard = true}

        val TableCards = PokerDeck.DECK.apply { shuffle() }.filter { it.information.isHandCard == false }.subList(0, 5)

        println(SingleHandCalc(FIRST, SECOND, TableCards))
        return

        Scanner(System.`in`).use {scanner ->

            println("- - Handkarten eingeben - -")
            repeat(2){
                print("Farbe (Karo, Herz, Pik, Kreuz): ")
                val color: String = scanner.nextLine()
                print("Nummer (A, 1-10, B, D, K): ")
                val number: String = scanner.nextLine()
                PokerDeck.getCardbyAttributes(Colors.byName(color), Numbers.Companion.byName(number)).apply {
                    information.isHandCard = true
                    println(this)
                }
            }



            println("- - Tischkarten eingeben - -")
            repeat(3){

            }


        }
    }



}