package com.example.myapplication

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.raycalendarview.BR

class CalendarViewModel : BaseObservable() {

    val dates: MutableList<CalendarMonthModel> = mutableListOf()

    @Bindable
    var month = ""
        set(value){
            field = value
            notifyPropertyChanged(BR.month)
        }

    @Bindable
    var year = ""
        set(value){
            field = value
            notifyPropertyChanged(BR.year)
        }

    @Bindable
    var view = ""

    var currentDateReyclerPos = -1

    fun setCurrentMonthAndYear(position: Int) {
        month = dates[position].month
        year = dates[position].year
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