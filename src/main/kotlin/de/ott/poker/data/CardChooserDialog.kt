package de.ott.poker.data

import javafx.event.EventHandler
import javafx.scene.Scene
import tornadofx.*
import javafx.scene.effect.BlendMode
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.stage.Stage

data class CardChooserDialogResult(val image: ImageView, val card: PokerCard)

class CardChooserDialog : View("Kartenwahl") {

    companion object{
        private var resultImage: ImageView? = null
        private var resultCard: PokerCard? = null

        fun showDialog(): CardChooserDialogResult {
            Stage().apply {
                scene = Scene(CardChooserDialog().root)
            }.showAndWait()
            return CardChooserDialogResult(resultImage!!, resultCard!!)
        }
    }

    override val root = vbox(20) {
        val vb = this

        prefHeight = Double.MAX_VALUE
        prefWidth = Double.MAX_VALUE

        hbox(10) {
            val hbox = this
            prefWidthProperty().bind(vb.widthProperty())
            prefHeightProperty().bind(vb.heightProperty().divide(4))
            Numbers.values().forEach {  add(getImageViewByCard(vb, hbox, PokerCard(it, Colors.KARO)))  }
        }
        hbox(10) {
            val hbox = this
            prefWidthProperty().bind(vb.widthProperty())
            prefHeightProperty().bind(vb.heightProperty().divide(4))
            Numbers.values().forEach {  add(getImageViewByCard(vb, hbox, PokerCard(it, Colors.HERZ)))  }
        }
        hbox(10) {
            val hbox = this
            prefWidthProperty().bind(vb.widthProperty())
            prefHeightProperty().bind(vb.heightProperty().divide(4))
            Numbers.values().forEach {  add(getImageViewByCard(vb, hbox, PokerCard(it, Colors.KREUZ)))  }
        }
        hbox(10) {
            val hbox = this
            prefWidthProperty().bind(vb.widthProperty())
            prefHeightProperty().bind(vb.heightProperty().divide(4))
            Numbers.values().forEach {  add(getImageViewByCard(vb, hbox, PokerCard(it, Colors.PIK)))  }
        }
    }

    private fun getImageViewByCard(vb: VBox, hbox: HBox, card: PokerCard): Pane {
        return pane {
            imageview {
                image = Image(card.getImageURL())

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
                    resultImage = this
                    resultCard = card
                    close()
                }
            }
        }
    }
}
