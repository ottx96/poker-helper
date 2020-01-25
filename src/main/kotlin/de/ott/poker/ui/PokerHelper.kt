package de.ott.poker.ui

import de.ott.poker.data.CardChooserDialog
import de.ott.poker.data.PokerCard
import javafx.event.EventHandler
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.ProgressBar
import javafx.scene.effect.BlendMode
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import tornadofx.*
import java.util.*


class PokerHelper: View("Poker Helper by Ott") {

        var firstCardImage: ImageView? = null
        var firstCard: PokerCard? = null

        var secondCardImage: ImageView? = null
        var secondCard: PokerCard? = null

        val tableCards = LinkedList<Pair<PokerCard, ImageView>>()

        val CHOSEN_IMAGE = "de.ott.poker.cards/green_back.png"

        override val root = vbox(75) {
                val vb = this
                style {
                        backgroundColor += Color.DARKSLATEGRAY
                }
                hbox {
                        spacingProperty().bind(vb.widthProperty().divide(10 * 4))
                        alignment = Pos.CENTER_LEFT
                        val hbox = this
                        prefHeightProperty().bind(vb.heightProperty().divide(1.45))

                        //Stapel
                        pane{
                                imageview {
                                        image = Image("de.ott.poker.cards/red_back.png")

                                        isPreserveRatio = true
                                        fitWidthProperty().bind(vb.widthProperty().divide(7.6))
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
                                                                CardChooserDialog.showDialog().apply {
                                                                        tableCards.add( card to image )
                                                                        add(image.apply {
                                                                                fitHeightProperty().bind(hbox.heightProperty())
                                                                                fitWidthProperty().bind(hbox.widthProperty() / 7.6)
                                                                        })
                                                                }
                                                        }
                                                )
                                        }
                                }
                        }

                        /// Platz nach rechts
                        label {
                                prefWidthProperty().bind(vb.widthProperty().divide(17))
                        }
                }
                hbox(30) {
                        style{
                                backgroundColor += Color.BEIGE
                        }
                        alignment = Pos.BOTTOM_LEFT
                        prefHeightProperty().bind(vb.heightProperty().divide(2.5))
                        val hbox = this
                        pane {
                                val pane = this
                                firstCardImage = imageview {
                                        image = Image("de.ott.poker.cards/gray_back.png")

                                        style{
                                                backgroundColor += Color(Math.random(),Math.random(),Math.random(),0.8)
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
                                                        firstCard = card
                                                }
                                                pane.replaceChildren(firstCardImage!!)
                                                firstCardImage!!.apply {
                                                        fitWidthProperty().bind(vb.widthProperty().divide(2))
                                                        fitHeightProperty().bind(hbox.heightProperty().divide(1))
                                                        image = Image(CHOSEN_IMAGE)
                                                        onMouseClicked = EventHandler {
                                                                if(firstCard!!.information.isVisible){
                                                                        image = Image(CHOSEN_IMAGE)
                                                                        firstCard!!.information.isVisible = false
                                                                }else{
                                                                        image = Image(firstCard!!.getImageURL())
                                                                        firstCard!!.information.isVisible = true
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
                                                backgroundColor += Color(Math.random(),Math.random(),Math.random(),0.8)
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
                                                        secondCard = card
                                                }
                                                pane.replaceChildren(secondCardImage!!)
                                                secondCardImage!!.apply {
                                                        fitWidthProperty().bind(vb.widthProperty().divide(2))
                                                        fitHeightProperty().bind(hbox.heightProperty().divide(1))
                                                        image = Image(CHOSEN_IMAGE)
                                                        onMouseClicked = EventHandler {
                                                                if(secondCard!!.information.isVisible){
                                                                        image = Image(CHOSEN_IMAGE)
                                                                        secondCard!!.information.isVisible = false
                                                                }else{
                                                                        image = Image(secondCard!!.getImageURL())
                                                                        secondCard!!.information.isVisible = true
                                                                }
                                                        }
                                                }
                                        }
                                }
                                add(secondCardImage!!)
                        }
                        borderpane {
                                prefWidthProperty().bind(hbox.widthProperty().divide(2))
                                right{
                                        button("Reset") {
                                                prefWidth = 200.0
                                        }
                                }
                        }
                }
        }
}