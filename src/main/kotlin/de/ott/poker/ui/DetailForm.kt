package de.ott.poker.ui

import de.ott.poker.data.PokerCard
import de.ott.poker.data.PokerDeck
import de.ott.poker.data.PokerHands
import de.ott.poker.impl.PokerHandCalcContainer
import de.ott.poker.impl.SingleHandCalc
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.paint.Color
import javafx.scene.text.FontPosture
import javafx.scene.text.FontWeight
import javafx.stage.Stage
import tornadofx.*
import java.util.*

class DetailForm : View("Details") {

    companion object{
        var firstCard: PokerCard? = null
        var secondCard: PokerCard? = null
        var tableCards: List<PokerCard>? = null

        fun show(first: PokerCard, second: PokerCard, table: List<PokerCard>){
            firstCard = first
            secondCard = second
            tableCards = table

            Stage().apply {
                isMaximized = true
                scene = Scene(DetailForm().root)
            }.showAndWait()
        }
    }

    val probabilities = LinkedList<PokerHandCalcContainer>().asObservable()

    init {
        PokerHands.values().forEach {
            probabilities.add(PokerHandCalcContainer(it))
        }

        PokerDeck.reset()

        //Tischkarten und Handkarten markieren
        PokerDeck.DECK.forEach { deckCard ->
            if (deckCard.color == firstCard!!.color && deckCard.number == firstCard!!.number)
                deckCard.information.isHandCard = true
            if (deckCard.color == secondCard!!.color && deckCard.number == secondCard!!.number)
                deckCard.information.isHandCard = true

            tableCards!!.forEach { tableCard ->
                if (deckCard.color == tableCard.color && deckCard.number == tableCard.number)
                    deckCard.information.isTableCard = true
            }
        }

        PokerDeck.DECK.filter { !it.information.isHandCard && !it.information.isTableCard }.forEach { card1 ->
            PokerDeck.DECK.filter { !it.information.isHandCard && !it.information.isTableCard && it != card1 }.forEach { card2 ->
                println("$card1 $card2 = ${SingleHandCalc(
                    card1,
                    card2,
                    tableCards!!
                ).getHighest()}")
            }
        }

    }

    override val root = borderpane {
        val bp = this
        style {
            backgroundColor += Color.BEIGE
        }
        top {
            val bpTop = this
            label("Details") {
                alignment = Pos.CENTER

                style{
                    prefWidthProperty().bind(bp.widthProperty())
                    prefHeightProperty().bind(bpTop.heightProperty().divide(5))
                    fontSize = 50.px
                    fontWeight = FontWeight.EXTRA_BOLD
                    fontStyle = FontPosture.ITALIC
                    fontFamily = "Consolas"
                }
            }
        }

        left {
            vbox {
                label("Eigene Chancen"){
                    alignment = Pos.CENTER
                    style {
                        fontSize = 30.px
                        fontWeight = FontWeight.BOLD
                        fontFamily = "Consolas"
                        fontStyle = FontPosture.ITALIC
                        prefWidthProperty().bind(bp.widthProperty().divide(2))
                    }
                }
                tableview<String> {
                    items.add(0, "Testinhalt 1")
                }
            }
        }
        right {
            vbox {
                label("Gegnerische Chancen"){
                    alignment = Pos.CENTER
                    style {
                        fontSize = 30.px
                        fontWeight = FontWeight.BOLD
                        fontFamily = "Consolas"
                        fontStyle = FontPosture.ITALIC
                        prefWidthProperty().bind(bp.widthProperty().divide(2))
                    }
                }

                tableview(probabilities) {
                    readonlyColumn("Bezeichnung", PokerHandCalcContainer::desc)
                    readonlyColumn("Wahrscheinlichkeit", PokerHandCalcContainer::probability)
                    readonlyColumn("Wahrscheinlichkeit", PokerHandCalcContainer::probability).useProgressBar()
                }
            }
        }

        bottom {
            toolbar {
                style{
                    prefWidthProperty().bind(bp.widthProperty())
                    backgroundColor += Color.BLUEVIOLET
                }
                button("Berechnen") {

                }
                button ("OK")
            }
        }

    }
}
