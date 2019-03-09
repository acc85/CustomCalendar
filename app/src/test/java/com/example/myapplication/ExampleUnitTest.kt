package com.example.myapplication

import org.junit.Test

import org.junit.Assert.*
import java.time.YearMonth
import java.util.*



/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun checkNumberOfWeeksInMonth(){
        var calendar:Calendar = Calendar.getInstance()
        assert(calendar.getActualMaximum(Calendar.WEEK_OF_MONTH) > 0)
//        assert(calendar.getNumberOvWeeksInMonth() >0)
    }

    @Test
    fun checkPopulatedCalendarMonth(){
        var calendarMonthModel = CalendarMonthModel()
        Calendar.getInstance().apply {
            var dayNumber = 1
            for(day in getFirstDayOfMonth()..6) {
                calendarMonthModel.rows[0][day] = dayNumber.toString()
                dayNumber++
            }
            calendarMonthModel.numberOfWeeks = getActualMaximum(Calendar.WEEK_OF_MONTH)
            var daysInMonth = getActualMaximum(Calendar.DAY_OF_MONTH)
            while(dayNumber < daysInMonth) {
                for (weekNumber in 1..calendarMonthModel.numberOfWeeks) {
                    for (dayInWeek in 0..6) {
                        calendarMonthModel.rows[weekNumber][dayInWeek] = dayNumber.toString()
                        dayNumber++
                    }
                }
            }
        }
        assert(calendarMonthModel.rows[0][0].isNotBlank())

    }
}
