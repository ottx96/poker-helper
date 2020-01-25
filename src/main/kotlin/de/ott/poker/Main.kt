package de.ott.poker

import de.ott.poker.ui.PokerHelper
import javafx.scene.text.FontWeight
import javafx.stage.Stage
import tornadofx.App
import tornadofx.Stylesheet
import tornadofx.c
import tornadofx.px


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