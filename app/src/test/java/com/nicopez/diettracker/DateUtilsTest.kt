package com.nicopez.diettracker

import com.nicopez.diettracker.ui.utils.DateUtils
import org.junit.Assert.*
import org.junit.Test

class DateUtilsTest {

    @Test
    fun `format duration in minutes`() {
        val thirtyMinutes = 30L * 60 * 1000
        assertEquals("30m", DateUtils.formatDuration(thirtyMinutes))
    }

    @Test
    fun `format duration in hours and minutes`() {
        val twoHoursThirtyMinutes = (2L * 60 * 60 * 1000) + (30 * 60 * 1000)
        assertEquals("2h 30m", DateUtils.formatDuration(twoHoursThirtyMinutes))
    }

    @Test
    fun `format duration in days, hours and minutes`() {
        val oneDayTwoHoursThirtyMinutes = (26L * 60 * 60 * 1000) + (30 * 60 * 1000)
        assertEquals("1d 2h 30m", DateUtils.formatDuration(oneDayTwoHoursThirtyMinutes))
    }

    @Test
    fun `format duration for multi-day fast`() {
        val threeDays = 72L * 60 * 60 * 1000
        assertEquals("3d 0h 0m", DateUtils.formatDuration(threeDays))
    }

    @Test
    fun `format duration for extended fast`() {
        val fiveDaysEightHours = (5L * 24 * 60 * 60 * 1000) + (8 * 60 * 60 * 1000)
        assertEquals("5d 8h 0m", DateUtils.formatDuration(fiveDaysEightHours))
    }
}
