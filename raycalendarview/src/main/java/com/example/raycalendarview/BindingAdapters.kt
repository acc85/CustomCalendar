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


@BindingAdapter(value = ["eventDrawable", "hasEvent"], requireAll = true)
fun setEventDrawableOnContent(view: TextView, hasEvent: Boolean) {
    if (hasEvent) {
        view.setCompoundDrawablesWithIntrinsicBounds(null,null,null,view.resources.getDrawable(R.drawable.event_drawable,null))
    } else {
        view.setCompoundDrawablesWithIntrinsicBounds(null,null,null,view.resources.getDrawable(R.drawable.transparent_event_drawable,null))

    }
}