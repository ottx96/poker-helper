package de.ott.poker.ui

import de.ott.poker.data.Calculations
import de.ott.poker.data.PokerCard
import de.ott.poker.calc.task.CurrentHandTask
import de.ott.poker.calc.task.MaxHandTask
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.effect.BlendMode
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import tornadofx.*
import java.util.*

class PokerHelper: View("Poker Helper by Ott") {
        var firstCardImage: ImageView? = null
        var handLeft: PokerCard? = null

        var secondCardImage: ImageView? = null
        var handRight: PokerCard? = null

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
                                                                CardChooserDialog.showDialog()
                                                                        .apply {
                                                                        tableCards.add( card to image )
                                                                        add(image.apply {
                                                                                fitHeightProperty().bind(hbox.heightProperty())
                                                                                fitWidthProperty().bind(hbox.widthProperty() / 7)
                                                                        })
                                                                }

                                                                // display current hand
                                                                val currHand = CurrentHandTask(tableCards.mapEach { first }.toMutableList().apply {
                                                                        add(handLeft!!)
                                                                        add(handRight!!)
                                                                })
                                                                currHand.setOnSucceeded {
                                                                        Calculations.lbl_currentHand.text = currHand.get().desc
                                                                }
                                                                Calculations.threads.execute(currHand)

                                                                // display max hand possible
                                                                val maxHand = MaxHandTask( listOf(handLeft!!, handRight!!), tableCards.mapEach { first })
                                                                maxHand.setOnSucceeded {
                                                                        Calculations.lbl_maxHand.text = maxHand.get().desc
                                                                }
                                                                Calculations.threads.execute(maxHand)
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
                                                // display current hand
                                                val currHand = CurrentHandTask(tableCards.mapEach { first }.toMutableList().apply {
                                                        add(handLeft!!)
                                                        add(handRight!!)
                                                })
                                                currHand.setOnSucceeded {
                                                        Calculations.lbl_currentHand.text = currHand.get().desc
                                                }
                                                Calculations.threads.execute(currHand)

                                                // display max hand possible
                                                val maxHand = MaxHandTask( listOf(handLeft!!, handRight!!), tableCards.mapEach { first })
                                                maxHand.setOnSucceeded {
                                                        Calculations.lbl_maxHand.text = maxHand.get().desc
                                                }
                                                Calculations.threads.execute(maxHand)

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
                                                        add(Calculations.lbl_currentHand)
                                                }
                                                var vb: VBox? = null
                                                vbox(2){
                                                        vb = this
                                                        label("Maximales Blatt")
                                                        add(Calculations.lbl_maxHand)
                                                }
                                                button("Details"){
                                                        prefWidthProperty().bind(vb!!.widthProperty().times(1.5))
                                                        prefHeightProperty().bind(vb!!.heightProperty())
                                                        action {
                                                                handRight?:handLeft?:return@action
                                                                DetailForm.show(
                                                                        handLeft!!,
                                                                        handRight!!,
                                                                        tableCards.mapEach { first })
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