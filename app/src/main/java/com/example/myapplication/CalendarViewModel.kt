package com.example.myapplication

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalendarViewModel : ViewModel() {

    val dates: MutableList<CalendarMonthModel> = mutableListOf()

    var month = ObservableField<String>()

    var year = ObservableField<String>()

    var view = ObservableField<View?>()

    fun setCurrentMonthAndYear(position: Int) {
        month.set(dates[position].month)
        year.set(dates[position].year)
    }

    var onDateChanged = MutableLiveData<DateObject>()

    data class DateObject(
        var day:String = "",
        var month:String = "",
        var year:String = ""
    )

}

//class CalendarViewModel : BaseObservable() {
//
//    val dates: MutableList<CalendarMonthModel> = mutableListOf()
//
//    @Bindable
//    var month: String = ""
//        set(value) {
//            field = value
//            notifyPropertyChanged(BR.month)
//        }
//
//    @Bindable
//    var year: String = ""
//        set(value) {
//            field = value
//            notifyPropertyChanged(BR.year)
//        }
//
//    @Bindable
//    var view: View? = null
//        set(value) {
//            field = value
//            notifyPropertyChanged(BR.view)
//        }
//
//    fun setCurrentMonthAndYear(position: Int) {
//        month = dates[position].month
//        year = dates[position].year
//    }
//
//    interface onDateChanged
//
//}