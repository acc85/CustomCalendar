package com.example.myapplication

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler



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


}