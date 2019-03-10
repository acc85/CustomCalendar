package com.example.myapplication

import java.util.*


fun Calendar.getFirstDayOfMonth(): Int {

    set(Calendar.DAY_OF_MONTH, 1)
    var calWeekNumber = get(Calendar.DAY_OF_WEEK)
    if (calWeekNumber == 1) {
        return 6
    }
    return calWeekNumber - 2
}


fun Calendar.setupCalendar(m: Int): CalendarMonthModel =
    CalendarMonthModel().also { calendarModel ->
        set(Calendar.MONTH, m)
        var dayNumber = 1
        for (day in getFirstDayOfMonth()..6) {
            calendarModel.rows[0][day] = dayNumber.toString()
            dayNumber++
        }
        var daysInMonth = getActualMaximum(Calendar.DAY_OF_MONTH)
        var weekNumber = 1
        while (dayNumber <= daysInMonth) {
            for (dayInWeek in 0..6) {
                if (dayNumber <= daysInMonth) {
                    calendarModel.rows[weekNumber][dayInWeek] = dayNumber.toString()
                    dayNumber++
                } else {
                    break
                }
            }
            weekNumber++
        }
        calendarModel.numberOfWeeks = weekNumber + 1
    }

