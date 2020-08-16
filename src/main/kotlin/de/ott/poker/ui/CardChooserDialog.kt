package de.ott.poker.ui

import de.ott.poker.data.enumerations.Numbers
import de.ott.poker.data.PokerCard
import de.ott.poker.data.enumerations.Color
import de.ott.poker.international.Translator
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

data class CardChooserDialogResult(val result: Map<ImageView, PokerCard>)

class CardChooserDialog(private val amount: Int) : View(Translator.get("card.chooser.dialog.title")) {

    init{
        result.clear()
    }

    companion object{
        private var result = mutableMapOf<ImageView, PokerCard>()

        fun showDialog(amount: Int = 1): CardChooserDialogResult {
            Stage().apply {
                isMaximized = true
                scene = Scene(CardChooserDialog(amount).root)
            }.showAndWait()
            return CardChooserDialogResult(result)
        }
    }

    override val root = vbox(20) {
        val vb = this

        hbox(10) {
            val hbox = this
            prefWidthProperty().bind(vb.widthProperty())
            prefHeightProperty().bind(vb.heightProperty().divide(4))
            Numbers.values().forEach {
                add(getImageViewByCard(vb, hbox,PokerCard(it,Color.CHECK) ))
            }
        }
        hbox(10) {
            val hbox = this
            prefWidthProperty().bind(vb.widthProperty())
            prefHeightProperty().bind(vb.heightProperty().divide(4))
            Numbers.values().forEach {
                add(getImageViewByCard(vb, hbox,PokerCard(it,Color.HEART) ))
            }
        }
        hbox(10) {
            val hbox = this
            prefWidthProperty().bind(vb.widthProperty())
            prefHeightProperty().bind(vb.heightProperty().divide(4))
            Numbers.values().forEach {
                add(getImageViewByCard(vb, hbox,PokerCard(it,Color.CROSS) ))
            }
        }
        hbox(10) {
            val hbox = this
            prefWidthProperty().bind(vb.widthProperty())
            prefHeightProperty().bind(vb.heightProperty().divide(4))
            Numbers.values().forEach {
                add(getImageViewByCard( vb, hbox, PokerCard(it,Color.PIK) ))
            }
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
                    if(result[this] == null) blendMode = null
                }
                onMouseClicked = EventHandler<MouseEvent>{
                    if(result[this] != null){
                        result.remove(this)
                    } else{
                        result[this] = card
                    }
                    if(result.size >= amount) close()
                }
            }
        }
    }
}
