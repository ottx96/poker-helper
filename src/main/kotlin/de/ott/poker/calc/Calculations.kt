package de.ott.poker.calc

import de.ott.poker.calc.task.CurrentHandTask
import de.ott.poker.calc.task.MaxHandTask
import de.ott.poker.data.PokerCard
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import tornadofx.mapEach
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object Calculations {
    val threads: ExecutorService = Executors.newCachedThreadPool()

    val labelCurrentHand = Label("N/A (unknown)")
    val labelMaxHand = Label("N/A (unknown)")

    fun calculateHands(handLeft: PokerCard?, handRight: PokerCard?, tableCards: LinkedList<Pair<PokerCard, ImageView>>){
        if(handLeft != null && handRight != null){
            // display current hand
            val currHand = CurrentHandTask(tableCards.mapEach { first }.toMutableList().apply {
                add(handLeft)
                add(handRight)
            })
            currHand.setOnSucceeded {
                labelCurrentHand.text = currHand.get().desc
            }
            threads.execute(currHand)

            // display max hand possible
            val maxHand = MaxHandTask( listOf(handLeft, handRight), tableCards.mapEach { first })
            maxHand.setOnSucceeded {
                labelMaxHand.text = maxHand.get().desc
            }
            threads.execute(maxHand)
        }
    }
}