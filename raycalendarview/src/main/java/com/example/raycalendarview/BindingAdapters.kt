package com.example.raycalendarview

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.myapplication.CalendarMonthModel

@BindingAdapter(value = ["backgroundDrawable", "dateValue"], requireAll = true)
fun setDrawableOnContent(view: TextView, calendarMonthModel: CalendarMonthModel, value: String) {
    if (value == calendarMonthModel.selectedate) {
        calendarMonthModel.setSelectedDate(view)
    } else {
        view.background = view.resources.getDrawable(R.drawable.transparent_drawable, null)
    }
}