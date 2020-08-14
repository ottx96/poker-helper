package de.ott.poker.calc

import javafx.scene.control.Label
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object Calculations {
    val threads: ExecutorService = Executors.newCachedThreadPool()

    val lbl_currentHand = Label("N/A (unknown)")
    val lbl_maxHand = Label("N/A (unknown)")
}