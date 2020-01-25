package de.ott.poker

import de.ott.poker.data.PokerDeck
import de.ott.poker.ui.PokerHelper
import javafx.scene.Scene
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import javafx.stage.Stage
import tornadofx.*


class Main: App(PokerHelper::class, Styles::class){
    override fun start(stage: Stage) {
        super.start(stage.apply {
            isFullScreen = true
            minHeight = 600.0
            minWidth = 900.0
        })
    }
}

class Styles : Stylesheet() {
    init {
        label {
            fontSize = 20.px
            fontWeight = FontWeight.BOLD
            backgroundColor += c(0,0,0,0.0)
        }
    }
}

//    val DECK = PokerDeck.DECK
//
//    @JvmStatic
//    fun main(args: Array<String>) {
//


//        val FIRST  = PokerDeck.getCardbyAttributes(Colors.KREUZ, Numbers.KOENIG).apply { information.isHandCard = true }
//        val SECOND = PokerDeck.getCardbyAttributes(Colors.KREUZ, Numbers.DAME).apply { information.isHandCard = true}
//
//        val TableCards = PokerDeck.DECK.apply { shuffle() }.filter { it.information.isHandCard == false }.subList(0, 5)
//
//        println(SingleHandCalc(FIRST, SECOND, TableCards))
//        return
//
//        Scanner(System.`in`).use {scanner ->
//
//            println("- - Handkarten eingeben - -")
//            repeat(2){
//                print("Farbe (Karo, Herz, Pik, Kreuz): ")
//                val color: String = scanner.nextLine()
//                print("Nummer (A, 1-10, B, D, K): ")
//                val number: String = scanner.nextLine()
//                PokerDeck.getCardbyAttributes(Colors.byName(color), Numbers.Companion.byName(number)).apply {
//                    information.isHandCard = true
//                    println(this)
//                }
//            }
//
//
//
//            println("- - Tischkarten eingeben - -")
//            repeat(3){
//
//            }
//
//
//        }
//    }



//}