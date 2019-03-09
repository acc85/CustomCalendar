package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FiveRowCalendarBinding

class CalendarPagerAdapter : RecyclerView.Adapter<CalendarPagerAdapter.CalendarViewHolder>() {

    var calendarViewModel: CalendarViewModel = CalendarViewModel()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder =
        CalendarViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.five_row_calendar,
                parent,
                false
            )
        )

    override fun getItemCount(): Int =
        calendarViewModel.dates.size

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind?.calendarModel = calendarViewModel.dates[holder.adapterPosition]
        holder.bind?.calendarViewModel = calendarViewModel
    }

    override fun onViewAttachedToWindow(holder: CalendarViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.bind?.invalidateAll()
    }


    override fun onViewDetachedFromWindow(holder: CalendarViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.bind?.calendarModel?.selectedView?.background = holder.bind?.root?.context?.resources?.getDrawable(R.drawable.transparent_drawable,null)
        holder.bind?.calendarModel?.selectedView = null
    }

    class CalendarViewHolder(dataBind: FiveRowCalendarBinding) : RecyclerView.ViewHolder(dataBind.root) {
        val bind: FiveRowCalendarBinding? = dataBind
    }
}