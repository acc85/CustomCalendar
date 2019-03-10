package com.example.myapplication

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.recyclerview.widget.LinearSmoothScroller.SNAP_TO_START
import androidx.recyclerview.widget.LinearSmoothScroller.SNAP_TO_ANY
import android.R.attr.y
import android.graphics.PointF
import androidx.recyclerview.widget.LinearSmoothScroller





class CalendarLinearLayourManager(context:Context, pos:Int):LinearLayoutManager(context){

    var startingPos = pos

    override fun onLayoutChildren(recycler: Recycler, state: RecyclerView.State) {
        if (startingPos != -1 && state.itemCount > 0) {

            scrollToPosition(startingPos)
            /*
            Data is present now, we can set the real scroll position
            */
            startingPos = -1
        }
        super.onLayoutChildren(recycler, state)
    }

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView, state: RecyclerView.State?,
        position: Int
    ) {
        val linearSmoothScroller = object : LinearSmoothScroller(recyclerView.context) {

            override fun getVerticalSnapPreference(): Int {
                return if (mTargetVector == null || mTargetVector.y == 0f)
                    SNAP_TO_ANY
                else
                    SNAP_TO_START
            }
        }
        linearSmoothScroller.targetPosition = position
        startSmoothScroll(linearSmoothScroller)
    }

}