package com.example.myapplication

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.raycalendarview.CalendarEventObject
import com.example.raycalendarview.R
import com.example.raycalendarview.databinding.CalendarDialogLayoutBinding
import kotlinx.android.synthetic.main.calendar_dialog_layout.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DateFormatSymbols
import java.util.*

class CalendarViewBuilder {

    lateinit var calendarViewModel: CalendarViewModel

    var calendarCallback: CalendarMonthModel.CalendarCallback? = null

    fun createCalendarViewDialog(activity: AppCompatActivity) {
        calendarViewModel = CalendarViewModel()
        createDialog(activity)
    }

    var eventList: MutableList<CalendarEventObject> = mutableListOf()
    set(value){
        field = value
        setupCalendarData()
    }


    fun createCalendarView(context: Context): View = DataBindingUtil.inflate<CalendarDialogLayoutBinding>(
        LayoutInflater.from(context),
        R.layout.calendar_dialog_layout,
        null,
        false
    ).also { databinding ->

        calendarViewModel = CalendarViewModel()
        setupCalendarData()
        databinding.root.apply {
            findViewById<RecyclerView>(R.id.calendar_pager).also { recyclerview ->
                recyclerview.setHasFixedSize(true)
                recyclerview.layoutParams.width = getWidthOfScreen()
                recyclerview.adapter =
                    CalendarPagerAdapter().also { calendarPagerAdapter ->
                        calendarPagerAdapter.calendarViewModel = calendarViewModel
                        if (calendarViewModel.month == "" && calendarViewModel.year == "") {
                            calendarViewModel.setCurrentMonthAndYear(0)
                        }
                    }
                databinding.calendarViewModel = calendarViewModel
                setupCalendarView(databinding.root)
                calendarCallback = object : CalendarMonthModel.CalendarCallback {
                    override fun OnDatechanged(day: String, month: String, year: String) {

                    }
                }
                leftButton.setOnClickListener {
                    recyclerview.smoothScrollToPosition((recyclerview.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() - 1)
                    calendarViewModel.setCurrentMonthAndYear((recyclerview.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() - 1)
                }
                rightButton.setOnClickListener {
                    recyclerview.smoothScrollToPosition((recyclerview.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() + 1)
                    calendarViewModel.setCurrentMonthAndYear((recyclerview.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() + 1)

                }
            }
        }
    }.root


    private fun getWidthOfScreen(): Int {
        return Resources.getSystem().getDisplayMetrics().widthPixels
    }


    private fun setupCalendarData(): Calendar {
        var currentYear = 0
        var currentMonth = 0
        var position = 0
        val dfs = DateFormatSymbols()
        val months = dfs.months
        return Calendar.getInstance().apply {
            currentYear = get(Calendar.YEAR)
            currentMonth = get(Calendar.MONTH)
            calendarViewModel.month = months[currentMonth]
            calendarViewModel.year = currentYear.toString()

            for (y in 0..200) {
                set(Calendar.YEAR, 1900 + y)
                for (m in 0..11) {
                    position++
                    calendarViewModel.dates.add(setupCalendar(m).also { calendarMonthModel ->
                        if (calendarViewModel.currentDateReyclerPos == -1) {
                            if (currentYear == get(Calendar.YEAR) && currentMonth == m + 1) {
                                calendarViewModel.currentDateReyclerPos = position
                            }
                        }
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

    private fun setupCalendarView(view: View) {
        view.findViewById<RecyclerView>(R.id.calendar_pager).apply {
            measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            layoutManager = CalendarLinearLayourManager(view.context, calendarViewModel.currentDateReyclerPos).apply {
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
            withContext(Dispatchers.IO) {
                setupCalendarData()
            }
            AlertDialog.Builder(context).create().apply {
                DataBindingUtil.inflate<CalendarDialogLayoutBinding>(
                    LayoutInflater.from(context),
                    R.layout.calendar_dialog_layout,
                    findViewById(android.R.id.content),
                    false
                ).also { databinding ->
                    databinding.root.findViewById<RecyclerView>(R.id.calendar_pager).also { recyclerview ->
                        recyclerview.adapter =
                            CalendarPagerAdapter().also { calendarPagerAdapter ->
                                calendarPagerAdapter.calendarViewModel = calendarViewModel
                                if (calendarViewModel.month == "" && calendarViewModel.year == "") {
                                    calendarViewModel.setCurrentMonthAndYear(0)
                                }
                            }
                        recyclerview.layoutParams = ConstraintLayout.LayoutParams(
                            context.resources.getDimension(R.dimen.recyclerview_dialog_with).toInt(),
                            MATCH_PARENT
                        )
                    }

                    databinding.calendarViewModel = calendarViewModel
                    setupCalendarView(databinding.root)
                    setView(databinding.root)

                    calendarCallback = object : CalendarMonthModel.CalendarCallback {
                        override fun OnDatechanged(day: String, month: String, year: String) {
                            dismiss()
                        }
                    }
                    show()
                }
            }


        }
    }

}