package com.example

import com.example.data.model.Player
import com.example.data.model.PlayerRole
import com.example.data.repository.FantasyData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testRoleConstraints() {
        assertEquals(1, PlayerRole.WK.minCount)
        assertEquals(4, PlayerRole.WK.maxCount)
        assertEquals(3, PlayerRole.BAT.minCount)
        assertEquals(6, PlayerRole.BAT.maxCount)
        assertEquals(1, PlayerRole.AR.minCount)
        assertEquals(4, PlayerRole.AR.maxCount)
        assertEquals(3, PlayerRole.BOWL.minCount)
        assertEquals(6, PlayerRole.BOWL.maxCount)
    }

    @Test
    fun testIndVsAusPlayersAvailable() {
        val players = FantasyData.indVsAusPlayers
        assertTrue("Players list should contain at least 22 players", players.size >= 22)
        val wkCount = players.count { it.role == PlayerRole.WK }
        val batCount = players.count { it.role == PlayerRole.BAT }
        val arCount = players.count { it.role == PlayerRole.AR }
        val bowlCount = players.count { it.role == PlayerRole.BOWL }

        assertTrue(wkCount >= 2)
        assertTrue(batCount >= 6)
        assertTrue(arCount >= 4)
        assertTrue(bowlCount >= 6)
    }

    @Test
    fun testContestCategories() {
        val contests = FantasyData.getContestsForMatch("match_ind_aus")
        assertTrue(contests.isNotEmpty())
        val megaContest = contests.find { it.category == com.example.data.model.ContestCategory.MEGA }
        assertTrue(megaContest != null)
        assertEquals(49, megaContest?.entryFee)
    }
}
