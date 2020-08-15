package de.ott.poker.calc

import de.ott.poker.calc.task.CurrentHandTask
import de.ott.poker.calc.task.EnemyChancesTask
import de.ott.poker.calc.task.MaxHandTask
import de.ott.poker.calc.task.OwnChancesTask
import de.ott.poker.data.PokerCard
import de.ott.poker.data.container.CalculationContainer
import de.ott.poker.data.enumerations.PokerHand
import javafx.scene.control.Label
import javafx.scene.control.TableView
import javafx.scene.image.ImageView
import tornadofx.*
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object Calculations {
    private val threads: ExecutorService = Executors.newCachedThreadPool()

    val labelCurrentHand by lazy { Label("N/A (unknown)") }
    val labelMaxHand by lazy { Label("N/A (unknown)") }

    val ownChancesContainers = observableListOf<CalculationContainer>()
    val enemyChancesContainers = observableListOf<CalculationContainer>()

    init {
        PokerHand.values().reversed().forEach {
            ownChancesContainers.add(CalculationContainer(it, -1.0))
            enemyChancesContainers.add(CalculationContainer(it, -1.0))
        }
    }

    var ownChancesTable: TableView<CalculationContainer>? = null
    var enemyChancesTable: TableView<CalculationContainer>? = null

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

            // update table (own chances)
            ownChancesContainers.forEach { threads.execute(
                    OwnChancesTask(it, tableCards.mapEach { first }.toMutableList().apply {
                        add(handLeft)
                        add(handRight)
                    }))
            }

            // update table (enemy chances)
            enemyChancesContainers.forEach { threads.execute(
                    EnemyChancesTask(it, tableCards.mapEach { first }, listOf(handLeft, handRight)))
            }

        }
    }

}