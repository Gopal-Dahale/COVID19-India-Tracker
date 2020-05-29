package com.bhagvada.covid19_india_tracker

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.district_item_list.view.*

class DistrictAdapter (val dlist: List<DistrictwiseItem>) : BaseAdapter(){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(parent?.context).inflate(R.layout.district_item_list,parent,false)
        val item = dlist[position]
        view.districtConfirmedTv.text = item.confirmed.toString()
        view.districtActiveTv.text = item.active.toString()
        view.districtDeathsTv.text = item.deceased.toString()
        view.districtRecoveredTv.text = item.recovered.toString()
        view.districtTv.text = item.districtName
        return view
    }

    override fun getItem(position: Int) = dlist[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = dlist.size

}