package de.ott.poker.ui

import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.text.FontPosture
import javafx.scene.text.FontWeight
import tornadofx.*

class DetailForm : View("Details") {
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

        center{
            left {
            }
            right {
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
