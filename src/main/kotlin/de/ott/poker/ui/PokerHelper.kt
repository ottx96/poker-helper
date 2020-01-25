package de.ott.poker.ui

import de.ott.poker.data.PokerCard
import de.ott.poker.impl.SingleHandCalc
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.effect.BlendMode
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.stage.Stage
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
                                                                CardChooserDialog.showDialog().apply {
                                                                        tableCards.add( card to image )
                                                                        add(image.apply {
                                                                                fitHeightProperty().bind(hbox.heightProperty())
                                                                                fitWidthProperty().bind(hbox.widthProperty() / 7)
                                                                        })
                                                                }
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
                                prefWidthProperty().bind(hbox.widthProperty().minus((hbox.children[0] as Pane).widthProperty()).minus((hbox.children[1] as Pane).widthProperty()).minus(50))
                                style{
                                        alignment = Pos.CENTER
                                        backgroundColor += Color.BEIGE
                                }
                                left{

                                }
                                bottom {
                                        val bp = this
                                        hbox(40){
                                                vbox(2){
                                                        val vb = this
                                                        label("Derzeitiges Blatt")
                                                        val lbl = label("N/A")
                                                        button ("berechnen"){
                                                                prefWidthProperty().bind(vb.widthProperty())
                                                                action {
                                                                        lbl.apply {
                                                                                firstCard?:secondCard?:return@apply
                                                                                text = SingleHandCalc(
                                                                                        firstCard!!,
                                                                                        secondCard!!,
                                                                                        tableCards.mapEach { first }).getHighest()
                                                                        }
                                                                }
                                                        }
                                                }
                                                var vb: VBox? = null
                                                vbox(2){
                                                        vb = this
                                                        label("Maximales Blatt")
                                                        val lbl = label("TODO: Straight Flush etc.")
                                                        button("berechnen") {
                                                                prefWidthProperty().bind(vb!!.widthProperty())
                                                                action{

                                                                }
                                                        }
                                                }
                                                button("Details"){
                                                        prefWidthProperty().bind(vb!!.widthProperty().times(1.5))
                                                        prefHeightProperty().bind(vb!!.heightProperty())
                                                        action {
                                                                Stage().apply {
                                                                        scene = Scene(DetailForm().root)
                                                                }.show()
                                                        }
                                                }
                                        }
                                        right{
                                                vbox {
                                                        imageview {
                                                                isPreserveRatio = true
                                                                image = Image("de.ott.poker.controls/PinClipart.com_newspaper-ad-clipart_2032112.png")
                                                                fitWidthProperty().bind(hbox.widthProperty().divide(15))
                                                                fitHeightProperty().bind(hbox.heightProperty().divide(1))
                                                                onMouseClicked = EventHandler {
                                                                        close()
                                                                }
                                                        }
                                                }
                                        }
                                }
                        }
                }
        }
}