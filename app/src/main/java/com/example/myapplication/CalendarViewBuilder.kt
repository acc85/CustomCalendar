package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.CalendarDialogLayoutBinding
import kotlinx.android.synthetic.main.calendar_dialog_layout.view.*
import kotlinx.coroutines.*
import java.text.DateFormatSymbols
import java.util.*

class CalendarViewBuilder {

    lateinit var calendarViewModel: CalendarViewModel

    var calendarCallback :CalendarMonthModel.CalendarCallback? = null

    fun createCalendaViewDialog(activity: AppCompatActivity) {
        calendarViewModel = ViewModelProviders.of(activity).get(CalendarViewModel::class.java)
        createDialog(activity)
    }


    private fun setupCalendarData():Deferred<Calendar> {
        var year = 0
        return GlobalScope.async {
            Calendar.getInstance().apply {
                for (y in 0..100) {
                    set(Calendar.YEAR, 1900 + y)
                    year = 1900 + y
                    for (m in 0..11) {
                        calendarViewModel.dates.add(setupCalendar(m).also { calendarMonthModel ->
                            val dfs = DateFormatSymbols()
                            val months = dfs.months
                            calendarMonthModel.month = months[get(Calendar.MONTH)]
                            calendarMonthModel.year = get(Calendar.YEAR).toString()
                            calendarMonthModel.calendarCallback = object : CalendarMonthModel.CalendarCallback {
                                override fun OnDatechanged(day: String, month: String, year: String) {
                                    calendarCallback?.OnDatechanged(day, month, year)
                                }
                            }
                        })
                    }
                }
            }
        }
    }

    private fun setupCalendarView(view: View) {
        view.findViewById<RecyclerView>(R.id.calendar_pager).apply{
            measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            layoutManager = LinearLayoutManager(view.context).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }

            object : PagerSnapHelper() {
                override fun findTargetSnapPosition(
                    layoutManager: RecyclerView.LayoutManager?,
                    velocityX: Int,
                    velocityY: Int
                ): Int {
                    val position = super.findTargetSnapPosition(layoutManager, velocityX, velocityY)
                    calendarViewModel.setCurrentMonthAndYear(position)
                    return position
                }
            }.attachToRecyclerView(view.findViewById(R.id.calendar_pager))
        }


    }


    private fun createDialog(context: Context) {
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO){
                setupCalendarData().await()
            }
            AlertDialog.Builder(context).create().apply {
                DataBindingUtil.inflate<CalendarDialogLayoutBinding>(LayoutInflater.from(context), R.layout.calendar_dialog_layout, findViewById(android.R.id.content), false).also { databinding ->
                    databinding.root.findViewById<RecyclerView>(R.id.calendar_pager).adapter =
                        CalendarPagerAdapter().also { calendarPagerAdapter ->
                            calendarPagerAdapter.calendarViewModel = calendarViewModel
                            calendarViewModel.month.set(calendarViewModel.dates[0].month)
                            calendarViewModel.year.set(calendarViewModel.dates[0].year)
//                            calendarPagerAdapter.notifyDataSetChanged()
                        }

                    databinding.calendarViewModel = calendarViewModel
                    setupCalendarView(databinding.root)
                    setView(databinding.root)
                    calendarCallback = object:CalendarMonthModel.CalendarCallback{
                        override fun OnDatechanged(day: String, month: String, year: String) {
                            dismiss()
                        }
                    }
                }
                show()
            }



        }

    }
}