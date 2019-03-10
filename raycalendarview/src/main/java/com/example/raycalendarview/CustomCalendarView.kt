package com.example.myapplication

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.raycalendarview.CalendarEventObject

class CustomCalendarView(context:Context, attributeSet: AttributeSet?, defStyle:Int) : ConstraintLayout(context, attributeSet, defStyle){

    var calendarViewBuilder = CalendarViewBuilder()

    constructor(context:Context):this(context,null,0)

    constructor(context:Context, attributeSet: AttributeSet?):this(context,attributeSet,0)


//    <TextView
//    android:id="@+id/monthName"
//    android:text="@{calendarViewModel.month}"
//    android:layout_width="wrap_content"
//    android:layout_height="wrap_content"
//    tools:text="January"
//    android:textStyle="bold"
//    android:textAlignment="center"
//    app:layout_constraintTop_toTopOf="@+id/leftButton"
//    app:layout_constraintBottom_toBottomOf="@+id/leftButton"
//    app:layout_constraintEnd_toStartOf="@+id/yearName"
//    app:layout_constraintStart_toStartOf="parent"
//    app:layout_constraintHorizontal_chainStyle="packed"
//    android:layout_marginEnd="4dp"/>

//    <TextView
//    android:id="@+id/yearName"
//    android:text="@{calendarViewModel.year}"
//    android:layout_width="wrap_content"
//    android:layout_height="wrap_content"
//    tools:text="1900"
//    android:textStyle="bold"
//    android:textAlignment="center"
//    android:layout_marginEnd="8dp"
//    app:layout_constraintTop_toTopOf="@+id/rightButton"
//    app:layout_constraintBottom_toBottomOf="@+id/rightButton"
//    app:layout_constraintEnd_toEndOf="parent"
//    app:layout_constraintStart_toEndOf="@+id/monthName"
//    android:layout_marginStart="4dp"/>

//    <ImageButton
//    app:layout_constraintTop_toTopOf="parent"
//    android:id="@+id/leftButton"
//    android:layout_width="wrap_content"
//    android:background="@drawable/ic_chevron_left_black_24dp"
//    android:layout_height="wrap_content" app:layout_constraintEnd_toStartOf="@+id/monthName"
//    android:layout_marginEnd="8dp" android:layout_marginTop="8dp"/>

    init{
        addView(calendarViewBuilder.createCalendarView(context))
    }

    fun setEventItems(eventList:MutableList<CalendarEventObject>){
        calendarViewBuilder.eventList = eventList
    }


}