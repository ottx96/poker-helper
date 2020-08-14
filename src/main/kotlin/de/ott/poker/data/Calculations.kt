package de.ott.poker.data

import javafx.scene.control.Label
import java.util.concurrent.Executors

object Calculations {
    val threads = Executors.newCachedThreadPool()

    val lbl_currentHand = Label("N/A (unknown)")
    val lbl_maxHand = Label("N/A (unknown)")
}