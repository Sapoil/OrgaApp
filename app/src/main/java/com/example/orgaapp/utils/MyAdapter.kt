package com.example.orgaapp.utils

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class MyAdapter<T>(activity: Activity, resource: Int, objects: Array<T>) : ArrayAdapter<T>(activity, resource, objects) {

    override fun isEnabled(position: Int): Boolean {
        return position != 0
    }

    override fun getDropDownView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val view = super.getDropDownView(position, convertView, parent) as TextView
        if (position == 0){
            view.setTextColor(Color.GRAY)
        }
        return view
    }

}