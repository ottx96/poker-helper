package de.ott.poker.ui

import de.ott.poker.calc.Calculations
import de.ott.poker.data.PokerCard
import de.ott.poker.data.container.CalculationContainer
import de.ott.poker.international.Translator
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.control.TableColumn
import javafx.scene.control.cell.ProgressBarTableCell
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.effect.BlendMode
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import tornadofx.*
import java.util.*


class PokerHelper: View("Poker Helper by Ott") {

        companion object{
                const val CHOSEN_IMAGE = "de.ott.poker.cards/green_back.png"
        }

        private var firstCardImage: ImageView? = null
        private var handLeft: PokerCard? = null

        private var secondCardImage: ImageView? = null
        private var handRight: PokerCard? = null

        private val tableCards = LinkedList<Pair<PokerCard, ImageView>>()

        override val root = vbox(75) {
                val vb = this
                style {
                        backgroundColor += Color.DARKSLATEGRAY
                }
                hbox {
                        spacingProperty().bind(vb.widthProperty().divide(10 * 4))
                        alignment = Pos.CENTER_LEFT
                        val hbox = this
                        prefHeightProperty().bind(vb.heightProperty().divide(1.7))

                        //Stapel
                        pane{
                                imageview {
                                        image = Image("de.ott.poker.cards/red_back.png")

                                        isPreserveRatio = true
                                        fitWidthProperty().bind(vb.widthProperty().divide(7))
                                        fitHeightProperty().bind(hbox.heightProperty())

                                        onMouseEntered = EventHandler {
                                                blendMode = BlendMode.SCREEN
                                        }
                                        onMouseExited = EventHandler {
                                                blendMode = null
                                        }
                                        onMouseClicked = EventHandler<MouseEvent>{
                                                CardChooserDialog.showDialog( if(tableCards.size == 0) 3 else 1 )
                                                        .apply {
                                                                result.forEach { (img, card) ->
                                                                        hbox.add(
                                                                                pane {
                                                                                        tableCards.add( card to img )
                                                                                        add(img.apply {
                                                                                                fitHeightProperty().bind(hbox.heightProperty())
                                                                                                fitWidthProperty().bind(hbox.widthProperty().divide(7))
                                                                                        })

                                                                                }
                                                                        )

                                                                }
                                                        }
                                                Calculations.calculateHands(handLeft, handRight, tableCards)
                                        }
                                }
                        }
                        /// Platz nach rechts
                        label {
                                prefWidthProperty().bind(vb.widthProperty().divide(28))
                        }
                }
                hbox(30) {
                        alignment = Pos.BOTTOM_LEFT
                        prefHeightProperty().bind(vb.heightProperty().divide(2.5))
                        val hbox = this

                        val paneLeftHand = pane {}
                        val paneRightHand = pane {}

                        val initialHandCardEventHandler = EventHandler<MouseEvent> {
                                with(CardChooserDialog.showDialog(2)) {
                                        result.forEach { (img, card) ->
                                                println(card)
                                                if (handLeft == null) {
                                                        println("setting left to: $card")
                                                        handLeft = card
                                                        firstCardImage = img
                                                        paneLeftHand.replaceChildren(firstCardImage!!)
                                                        firstCardImage!!.apply {
                                                                fitWidthProperty().bind(vb.widthProperty().divide(2))
                                                                fitHeightProperty().bind(hbox.heightProperty().divide(1))
                                                                image = Image(CHOSEN_IMAGE)
                                                                onMouseClicked = EventHandler {
                                                                        if (handLeft!!.information.isVisible) {
                                                                                image = Image(CHOSEN_IMAGE)
                                                                                handLeft!!.information.isVisible = false
                                                                        } else {
                                                                                image = Image(handLeft!!.getImageURL())
                                                                                handLeft!!.information.isVisible = true
                                                                        }
                                                                }
                                                        }
                                                } else if (handRight == null) {
                                                        println("setting right to: $card")
                                                        handRight = card
                                                        secondCardImage = img
                                                        paneRightHand.replaceChildren(secondCardImage!!)
                                                        secondCardImage!!.apply {
                                                                fitWidthProperty().bind(vb.widthProperty().divide(2))
                                                                fitHeightProperty().bind(hbox.heightProperty().divide(1))
                                                                image = Image(CHOSEN_IMAGE)
                                                                onMouseClicked = EventHandler {
                                                                        if (handRight!!.information.isVisible) {
                                                                                image = Image(CHOSEN_IMAGE)
                                                                                handRight!!.information.isVisible = false
                                                                        } else {
                                                                                image = Image(handRight!!.getImageURL())
                                                                                handRight!!.information.isVisible = true
                                                                        }
                                                                }
                                                        }
                                                }
                                        }
                                }
                        }

                        paneLeftHand.apply {
                                firstCardImage = imageview {
                                        image = Image("de.ott.poker.cards/gray_back.png")

                                        style {
                                                alignment = Pos.BOTTOM_CENTER
                                        }

                                        isPreserveRatio = true
                                        fitWidthProperty().bind(vb.widthProperty().divide(2))
                                        fitHeightProperty().bind(hbox.heightProperty().divide(1.4))

                                        onMouseEntered = EventHandler {
                                                blendMode = BlendMode.SCREEN
                                        }
                                        onMouseExited = EventHandler {
                                                blendMode = null
                                        }
                                        onMouseClicked = initialHandCardEventHandler
                                }
                                add(firstCardImage!!)
                        }
                        paneRightHand.apply {
                                secondCardImage = imageview {
                                        image = Image("de.ott.poker.cards/gray_back.png")

                                        style {
                                                alignment = Pos.BOTTOM_CENTER
                                        }

                                        isPreserveRatio = true
                                        fitWidthProperty().bind(vb.widthProperty().divide(2))
                                        fitHeightProperty().bind(hbox.heightProperty().divide(1.4))

                                        onMouseEntered = EventHandler {
                                                blendMode = BlendMode.SCREEN
                                        }
                                        onMouseExited = EventHandler {
                                                blendMode = null
                                        }
                                        onMouseClicked = initialHandCardEventHandler
                                }
                                add(secondCardImage!!)
                        }

                        borderpane {
                                prefWidthProperty().bind(hbox.widthProperty().minus((hbox.children[0] as Pane).widthProperty()).minus((hbox.children[1] as Pane).widthProperty()).minus(50))
                                style{
                                        alignment = Pos.CENTER
                                        backgroundColor += Color.GHOSTWHITE
                                }
                                center{
                                        splitpane{
                                                vbox(3){
                                                        val vb = this
                                                        val l = label(Translator.get("probability.opponent")){
                                                                prefWidthProperty().bind(vb.widthProperty())
                                                                alignment = Pos.CENTER
                                                        }
                                                        tableview(Calculations.enemyChancesContainers){
                                                                Calculations.enemyChancesTable = this
                                                                style { prefHeightProperty().bind(vb.heightProperty().minus(l.heightProperty())) }

                                                                val progressCol = TableColumn<CalculationContainer, Double>(Translator.get("probability")).apply{
                                                                        cellValueFactory = PropertyValueFactory<CalculationContainer, Double>("probability")
                                                                        cellFactory = ProgressBarTableCell.forTableColumn<CalculationContainer>()

                                                                        prefWidthProperty().bind(Calculations.enemyChancesTable!!.widthProperty().divide(3))
                                                                }
                                                                column(Translator.get("poker.hand"), CalculationContainer::hand){
                                                                        prefWidthProperty().bind(Calculations.enemyChancesTable!!.widthProperty().divide(3))
                                                                }
                                                                columns.add(progressCol)
                                                                column(Translator.get("probability"), CalculationContainer::probability){
                                                                        prefWidthProperty().bind(Calculations.enemyChancesTable!!.widthProperty().divide(3))
                                                                }
                                                        }
                                                }

                                                vbox(3){
                                                        val vb = this
                                                        val l = label(Translator.get("probability.own")){
                                                                prefWidthProperty().bind(vb.widthProperty())
                                                                alignment = Pos.CENTER
                                                        }
                                                        tableview(Calculations.ownChancesContainers){
                                                                Calculations.ownChancesTable = this
                                                                style { prefHeightProperty().bind(vb.heightProperty().minus(l.heightProperty())) }

                                                                val progressCol = TableColumn<CalculationContainer, Double>(Translator.get("probability")).apply{
                                                                        cellValueFactory = PropertyValueFactory<CalculationContainer, Double>("probability")
                                                                        cellFactory = ProgressBarTableCell.forTableColumn<CalculationContainer>()
                                                                        prefWidthProperty().bind(Calculations.ownChancesTable!!.widthProperty().divide(3))
                                                                }
                                                                column(Translator.get("poker.hand"), CalculationContainer::hand){
                                                                        prefWidthProperty().bind(Calculations.ownChancesTable!!.widthProperty().divide(3))
                                                                }
                                                                columns.add(progressCol)
                                                                column(Translator.get("probability"), CalculationContainer::probability){
                                                                        prefWidthProperty().bind(Calculations.ownChancesTable!!.widthProperty().divide(3))
                                                                }
                                                        }
                                                }
                                        }

                                }
                                right {
                                        borderpane {
                                                bottom{
                                                        vbox(40){
                                                                vbox(2){
                                                                        label(Translator.get("poker.hand.current"))
                                                                        add(Calculations.labelCurrentHand)
                                                                }
                                                                vbox(2){
                                                                        label(Translator.get("poker.hand.max"))
                                                                        add(Calculations.labelMaxHand)
                                                                }
                                                        }
                                                }
                                        }
                                }
                        }
                }
        }
}