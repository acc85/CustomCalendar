package com.example.myapplication

import android.content.Context
import android.text.format.Time
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.util.*


/**
 * Given a context and a time in millis since unix epoch figures out the
 * correct week of the year for that time.
 *
 * @param millisSinceEpoch
 * @return
 */
fun getWeekNumberFromTime(millisSinceEpoch: Long, context: Context): Int {
    val weekTime = Time(TimeZone.getDefault().id)
    weekTime.set(millisSinceEpoch)
    weekTime.normalize(true)
    val firstDayOfWeek = getFirstDayOfWeek()
    // if the date is on Saturday or Sunday and the start of the week
    // isn't Monday we may need to shift the date to be in the correct
    // week
    if (weekTime.weekDay === Time.SUNDAY && (firstDayOfWeek == Time.SUNDAY || firstDayOfWeek == Time.SATURDAY)) {
        weekTime.monthDay++
        weekTime.normalize(true)
    } else if (weekTime.weekDay === Time.SATURDAY && firstDayOfWeek == Time.SATURDAY) {
        weekTime.monthDay += 2
        weekTime.normalize(true)
    }
    return weekTime.getWeekNumber()
}

/**
 * Get first day of week as android.text.format.Time constant.
 *
 * @return the first day of week in android.text.format.Time
 */
fun getFirstDayOfWeek(): Int {
    val startDay: Int
    startDay = Calendar.getInstance().firstDayOfWeek

    return if (startDay == Calendar.SATURDAY) {
        Time.SATURDAY
    } else if (startDay == Calendar.MONDAY) {
        Time.MONDAY
    } else {
        Time.SUNDAY
    }
}


fun Calendar.getFirstDayOfMonth(): Int {

    set(Calendar.DAY_OF_MONTH, 1)
    var calWeekNumber = get(Calendar.DAY_OF_WEEK)
    if (calWeekNumber == 1) {
        return 6
    }
    return calWeekNumber - 2
}

fun Calendar.getNumberOvWeeksInMonth(): Int {
    val start = get(Calendar.WEEK_OF_MONTH)
    add(Calendar.MONTH, 1)
    add(Calendar.DATE, -1)
    val end = get(Calendar.WEEK_OF_MONTH)
    return (end - start + 1)
}


@BindingAdapter(value = ["backgroundDrawable", "dateValue"], requireAll = true)
fun setDrawableOnContent(view: TextView, calendarMonthModel: CalendarMonthModel, value: String) {
    if (value == "1") {
        calendarMonthModel.setSelectedDate(view)
    } else {
        view.background = view.resources.getDrawable(R.drawable.transparent_drawable, null)
    }
}


fun Calendar.setupCalendar(m: Int): CalendarMonthModel =
    CalendarMonthModel().apply {
        set(Calendar.MONTH, m)
        var dayNumber = 1
        for (day in getFirstDayOfMonth()..6) {
            rows[0][day] = dayNumber.toString()
            dayNumber++
        }
        numberOfWeeks = getNumberOvWeeksInMonth()
        var daysInMonth = getActualMaximum(Calendar.DAY_OF_MONTH)
        for (weekNumber in 2..numberOfWeeks) {
            for (dayInWeek in 0..6) {
                if (dayNumber <= daysInMonth) {
                    rows[weekNumber - 1][dayInWeek] = dayNumber.toString()
                    dayNumber++
                } else {
                    break
                }
            }
        }
    }


