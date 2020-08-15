package de.ott.poker.calc.task

import de.ott.poker.calc.Calculations
import de.ott.poker.data.PokerCard
import de.ott.poker.data.enumerations.Color
import de.ott.poker.data.enumerations.Numbers
import de.ott.poker.data.enumerations.PokerHand
import org.apache.commons.lang3.builder.ToStringBuilder
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

internal class OwnChancesTaskTest {

    @BeforeEach
    fun setUp() {

    }

    @AfterEach
    fun tearDown() {

    }

    @Test
    fun `test own chances straight flush`() {

        val table = listOf(
            PokerCard(Numbers.THREE, Color.HERZ),
            PokerCard(Numbers.FOUR, Color.HERZ),
            PokerCard(Numbers.SEVEN, Color.HERZ)
        )

        val hand = listOf(
            PokerCard(Numbers.FIVE, Color.HERZ),
            PokerCard(Numbers.SIX, Color.HERZ)
        )

        val T = Executors.newFixedThreadPool(Calculations.ownChancesContainers.size)

        Calculations.ownChancesContainers.forEach {
            T.execute {
                OwnChancesTask(it, table.toMutableList().apply { addAll(hand) }).testCall()
                println(ToStringBuilder.reflectionToString(it))
            }
        }

        T.shutdown()
        T.awaitTermination(20, TimeUnit.SECONDS)

        assert( Calculations.ownChancesContainers.first { it.hand == PokerHand.HIGH_CARD }.probability == 1.0 )
        assert( Calculations.ownChancesContainers.first { it.hand == PokerHand.STRAIGHT }.probability == 1.0 )
        assert( Calculations.ownChancesContainers.first { it.hand == PokerHand.FLUSH }.probability == 1.0 )
        assert( Calculations.ownChancesContainers.first { it.hand == PokerHand.STRAIGHT_FLUSH }.probability == 1.0 )
    }
}