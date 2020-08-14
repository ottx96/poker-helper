package de.ott.poker.data

import javafx.scene.control.Label
import java.util.concurrent.Executors

object Calculations {
    val threads = Executors.newFixedThreadPool(4)
    val lbl_currentHand = Label("N/A (unknown)")

//    var maxHand: Any = null!! // TODO
    val lbl_maxHand = Label("N/A (unknown)")

//    var details: Any = null!! // TODO
}