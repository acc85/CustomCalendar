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
import kotlinx.coroutines.*
import java.text.DateFormatSymbols
import java.util.*

class CalendarViewBuilder {

    lateinit var calendarViewModel: CalendarViewModel

    fun createCalendaViewDialog(activity: AppCompatActivity) {
        calendarViewModel = ViewModelProviders.of(activity).get(CalendarViewModel::class.java)
        createDialog(activity)
    }

    private fun setupCalendarView(view: View, alertDialog: AlertDialog) {
        var year: Int = 0
        view.findViewById<RecyclerView>(R.id.calendar_pager).setHasFixedSize(true)
        view.findViewById<RecyclerView>(R.id.calendar_pager).layoutManager = LinearLayoutManager(view.context).apply {
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

        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO){
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
                                        alertDialog.dismiss()
                                    }
                                }
                            })
                        }
                    }

                }
            }
            view.findViewById<RecyclerView>(R.id.calendar_pager).adapter =
                CalendarPagerAdapter().also { calendarPagerAdapter ->
                    calendarPagerAdapter.calendarViewModel = calendarViewModel
                    calendarViewModel.month.set(calendarViewModel.dates[0].month)
                    calendarViewModel.year.set(calendarViewModel.dates[0].year)
                    calendarPagerAdapter.notifyDataSetChanged()
                }
        }
    }


    private fun createDialog(context: Context) {
        AlertDialog.Builder(context).create().apply {
            DataBindingUtil.inflate<CalendarDialogLayoutBinding>(
                LayoutInflater.from(context),
                R.layout.calendar_dialog_layout,
                findViewById(android.R.id.content),
                false
            ).also { databinding ->
                databinding.calendarViewModel = calendarViewModel
                setupCalendarView(databinding.root, this)
                setView(databinding.root)
            }
            show()
        }
    }
}