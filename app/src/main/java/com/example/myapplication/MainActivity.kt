package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.raycalendarview.CalendarEventObject
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        custom_calendar_view.setEventItems(mutableListOf(CalendarEventObject()))
//        CalendarViewBuilder().createCalendarViewDialog(this)
    }

}


