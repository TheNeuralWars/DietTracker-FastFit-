package com.nicopez.diettracker

import com.nicopez.diettracker.data.entity.FastingSession
import org.junit.Assert.*
import org.junit.Test

class FastingSessionTest {

    @Test
    fun `active fasting session has null endTime`() {
        val session = FastingSession(
            startTime = System.currentTimeMillis()
        )
        assertTrue(session.isActive())
        assertNull(session.endTime)
    }

    @Test
    fun `completed fasting session has endTime`() {
        val startTime = System.currentTimeMillis()
        val endTime = startTime + (24 * 60 * 60 * 1000) // 24 hours later
        val session = FastingSession(
            startTime = startTime,
            endTime = endTime
        )
        assertFalse(session.isActive())
        assertNotNull(session.endTime)
    }

    @Test
    fun `duration calculation for completed session`() {
        val startTime = System.currentTimeMillis()
        val endTime = startTime + (12 * 60 * 60 * 1000) // 12 hours later
        val session = FastingSession(
            startTime = startTime,
            endTime = endTime
        )
        assertEquals(12.0, session.durationHours(), 0.1)
    }

    @Test
    fun `duration calculation for multi-day fast`() {
        val startTime = System.currentTimeMillis()
        val endTime = startTime + (72 * 60 * 60 * 1000) // 72 hours (3 days) later
        val session = FastingSession(
            startTime = startTime,
            endTime = endTime
        )
        assertEquals(72.0, session.durationHours(), 0.1)
    }

    @Test
    fun `duration calculation for active session uses current time`() {
        val startTime = System.currentTimeMillis() - (6 * 60 * 60 * 1000) // Started 6 hours ago
        val session = FastingSession(
            startTime = startTime
        )
        // Active session should calculate duration up to current time
        val duration = session.durationHours()
        assertTrue(duration >= 5.9 && duration <= 6.1) // Allow small variance for test execution time
    }
}
