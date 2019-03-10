package com.example.myapplication

import android.view.View
import android.widget.TextView
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

data class CalendarMonthModel(

    var year: String = "",

    var month: String = "",

    var rows: Array<Array<String>> = arrayOf(
        arrayOf("0", "0", "0", "0", "0", "0", "0"),
        arrayOf("0", "0", "0", "0", "0", "0", "0"),
        arrayOf("0", "0", "0", "0", "0", "0", "0"),
        arrayOf("0", "0", "0", "0", "0", "0", "0"),
        arrayOf("0", "0", "0", "0", "0", "0", "0"),
        arrayOf("0", "0", "0", "0", "0", "0", "0")
    ),

    var numberOfWeeks: Int = 5,


    var selectedate: String = "1",

    var calendarCallback:CalendarCallback? = null


) : BaseObservable() {


    var selectedView: View? = null

    @Bindable
    var selectedDate: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.selectedDate)
        }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CalendarMonthModel

        if (!rows.contentEquals(other.rows)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = rows.contentHashCode()
        return result
    }


    fun getValue(value: String): String =
        if (value == "0") "" else value


    fun getBottomRowVisibility(): Int =
        if (numberOfWeeks < 5)
            View.GONE
        else
            View.VISIBLE

    fun setSelectedDate(view:TextView){
        if (view != selectedView && view.text.isNotBlank()) {
            view.background = view.resources.getDrawable(R.drawable.circle_image, null)
            selectedView?.background = view.resources.getDrawable(R.drawable.transparent_drawable, null)
            selectedView = view
        }
    }

    fun onViewClicked(view: TextView) {
        setSelectedDate(view)
        calendarCallback?.OnDatechanged(view.text.toString(),month,year)
    }

    interface CalendarCallback {

        fun OnDatechanged(day: String, month: String, year: String)

    }

}