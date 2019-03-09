package com.example.myapplication

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.*
import com.example.myapplication.databinding.CalendarDialogLayoutBinding
import kotlinx.android.synthetic.main.calendar_dialog_layout.*
import java.text.DateFormatSymbols
import java.util.*


class MainActivity : AppCompatActivity() {

    private var year : Int = 0
    lateinit var calendarViewModel:CalendarViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        calendarViewModel = ViewModelProviders.of(this).get(CalendarViewModel::class.java)
//        DataBindingUtil.setContentView<CalendarDialogLayoutBinding>(this,R.layout.calendar_dialog_layout).calendarViewModel = calendarViewModel
        createDialog()
    }

    fun setupCalendarView(view:View){

        view.findViewById<RecyclerView>(R.id.calendar_pager).setHasFixedSize(true)
        view.findViewById<RecyclerView>(R.id.calendar_pager).layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }

        object: PagerSnapHelper() {
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

        Calendar.getInstance().apply {
            Calendar.getInstance().apply {
                for(y in 0..100){
                    set(Calendar.YEAR, 1900+y)
                    year = 1900+y
                    for(m in 0..11) {
                        calendarViewModel.dates.add(setupCalendar(m).also { calendarMonthModel ->
                            val dfs = DateFormatSymbols()
                            val months = dfs.months
                            calendarMonthModel.month = months[get(Calendar.MONTH)]
                            calendarMonthModel.year = get(Calendar.YEAR).toString()
                        })
                    }
                }
            }
            view.findViewById<RecyclerView>(R.id.calendar_pager).adapter = CalendarPagerAdapter().also { calendarPagerAdapter ->
                calendarPagerAdapter.calendarViewModel = calendarViewModel
                calendarViewModel.month.set(calendarViewModel.dates[0].month)
                calendarViewModel.year.set(calendarViewModel.dates[0].year)
                calendarPagerAdapter.notifyDataSetChanged()
            }
        }
    }

    fun createDialog(){
        AlertDialog.Builder(this).apply {
            DataBindingUtil.inflate<CalendarDialogLayoutBinding>(LayoutInflater.from(context),R.layout.calendar_dialog_layout,findViewById(android.R.id.content),false).also {databinding->
                databinding.calendarViewModel = calendarViewModel
                setupCalendarView(databinding.root)
                setView(databinding.root)
            }
        }.create().apply {
            show()
        }
    }

    private fun px(px:Float)=
        (resources.displayMetrics.density* px).toInt()


    private fun Calendar.setupCalendar(m: Int):CalendarMonthModel  =
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

    }


