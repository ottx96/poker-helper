package de.ott.poker.ui

import de.ott.poker.calc.Calculations
import de.ott.poker.data.PokerCard
import de.ott.poker.data.container.CalculationContainer
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.control.TableCell
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
                                                hbox.add(
                                                        pane {
                                                                CardChooserDialog.showDialog()
                                                                        .apply {
                                                                        tableCards.add( card to image )
                                                                        add(image.apply {
                                                                                fitHeightProperty().bind(hbox.heightProperty())
                                                                                fitWidthProperty().bind(hbox.widthProperty() / 7)
                                                                        })
                                                                }
                                                                Calculations.calculateHands(handLeft, handRight, tableCards)
                                                        }
                                                )
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
                        pane {
                                val pane = this
                                firstCardImage = imageview {
                                        image = Image("de.ott.poker.cards/gray_back.png")

                                        style{
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
                                        onMouseClicked = EventHandler<MouseEvent>{
                                                with(CardChooserDialog.showDialog()){
                                                        firstCardImage = image
                                                        handLeft = card
                                                }
                                                pane.replaceChildren(firstCardImage!!)
                                                firstCardImage!!.apply {
                                                        fitWidthProperty().bind(vb.widthProperty().divide(2))
                                                        fitHeightProperty().bind(hbox.heightProperty().divide(1))
                                                        image = Image(CHOSEN_IMAGE)
                                                        onMouseClicked = EventHandler {
                                                                if(handLeft!!.information.isVisible){
                                                                        image = Image(CHOSEN_IMAGE)
                                                                        handLeft!!.information.isVisible = false
                                                                }else{
                                                                        image = Image(handLeft!!.getImageURL())
                                                                        handLeft!!.information.isVisible = true
                                                                }
                                                        }
                                                }
                                        }
                                }
                                add(firstCardImage!!)
                        }
                        pane {
                                val pane = this
                                secondCardImage = imageview {
                                        image = Image("de.ott.poker.cards/gray_back.png")

                                        style{
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
                                        onMouseClicked = EventHandler<MouseEvent>{
                                                with(CardChooserDialog.showDialog()){
                                                        secondCardImage = image
                                                        handRight = card
                                                }
                                                pane.replaceChildren(secondCardImage!!)
                                                secondCardImage!!.apply {
                                                        fitWidthProperty().bind(vb.widthProperty().divide(2))
                                                        fitHeightProperty().bind(hbox.heightProperty().divide(1))
                                                        image = Image(CHOSEN_IMAGE)
                                                        onMouseClicked = EventHandler {
                                                                if(handRight!!.information.isVisible){
                                                                        image = Image(CHOSEN_IMAGE)
                                                                        handRight!!.information.isVisible = false
                                                                }else{
                                                                        image = Image(handRight!!.getImageURL())
                                                                        handRight!!.information.isVisible = true
                                                                }
                                                        }
                                                }
                                        }
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
                                                val x = listOf(CalculationContainer(), CalculationContainer()).asObservable()
                                                vbox(3){
                                                        val vb = this
                                                        val x = listOf(CalculationContainer(), CalculationContainer()).asObservable()
                                                        val l = label("Gegnerische Wahrscheinlichkeiten"){
                                                                prefWidthProperty().bind(vb.widthProperty())
                                                                alignment = Pos.CENTER
                                                        }
                                                        tableview(x){
                                                                val tv = this
                                                                style { prefHeightProperty().bind(vb.heightProperty().minus(l.heightProperty())) }

                                                                val progressCol = TableColumn<CalculationContainer, Double>("Probability").apply{
                                                                        cellValueFactory = PropertyValueFactory<CalculationContainer, Double>("probability")
                                                                        cellFactory = ProgressBarTableCell.forTableColumn<CalculationContainer>()

                                                                        prefWidthProperty().bind(tv.widthProperty().divide(3))
                                                                }
                                                                column("PokerHand", CalculationContainer::hand){
                                                                        prefWidthProperty().bind(tv.widthProperty().divide(3))
                                                                }
                                                                columns.add(progressCol)
                                                                column("Probability", CalculationContainer::probability){
                                                                        prefWidthProperty().bind(tv.widthProperty().divide(3))
                                                                }
                                                        }
                                                }

                                                vbox(3){
                                                        val vb = this
                                                        val x = listOf(CalculationContainer(), CalculationContainer()).asObservable()
                                                        val l = label("Eigene Wahrscheinlichkeiten"){
                                                                prefWidthProperty().bind(vb.widthProperty())
                                                                alignment = Pos.CENTER
                                                        }
                                                        tableview(x){
                                                                val tv = this
                                                                style { prefHeightProperty().bind(vb.heightProperty().minus(l.heightProperty())) }

                                                                val progressCol = TableColumn<CalculationContainer, Double>("Probability").apply{
                                                                        cellValueFactory = PropertyValueFactory<CalculationContainer, Double>("probability")
                                                                        cellFactory = ProgressBarTableCell.forTableColumn<CalculationContainer>()
                                                                        prefWidthProperty().bind(tv.widthProperty().divide(3))
                                                                }
                                                                column("PokerHand", CalculationContainer::hand){
                                                                        prefWidthProperty().bind(tv.widthProperty().divide(3))
                                                                }
                                                                columns.add(progressCol)
                                                                column("Probability", CalculationContainer::probability){
                                                                        prefWidthProperty().bind(tv.widthProperty().divide(3))
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
                                                                        label("Derzeitiges Blatt")
                                                                        add(Calculations.labelCurrentHand)
                                                                }
                                                                vbox(2){
                                                                        label("Maximales Blatt")
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