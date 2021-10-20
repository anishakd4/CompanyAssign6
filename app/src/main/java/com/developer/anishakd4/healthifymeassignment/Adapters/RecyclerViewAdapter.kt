package com.developer.anishakd4.healthifymeassignment.Adapters

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.developer.anishakd4.healthifymeassignment.Model.Dates
import com.developer.anishakd4.healthifymeassignment.R
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.android.synthetic.main.list_item_header.view.*
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.collections.ArrayList


class RecyclerViewAdapter(val keys: ArrayList<String>, var dates: HashMap<String, ArrayList<Dates>>, val context: Context)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_SLOT = 1
    }

    val arrayList: ArrayList<Dates> = ArrayList<Dates>()
    val keyBool: ArrayList<Boolean> = ArrayList<Boolean>()

    init {
        for(key in keys){
            keyBool.add(true)
            if(dates[key] != null){
                for (value in dates[key]!!){
                    arrayList.add(value)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == TYPE_HEADER){
            return RecyclerViewAdapterViewHolder2(LayoutInflater.from(parent.context).inflate(R.layout.list_item_header, parent, false))
        }else{
            return RecyclerViewAdapterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
        }

    }

    override fun getItemCount(): Int {
        var count = 0
        count = count + keys.size
        count = count + arrayList.size
        return count
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(position < keys.size){
            (holder as RecyclerViewAdapterViewHolder2).bind(keys.get(position), dates)
            (holder as RecyclerViewAdapterViewHolder2).switchKey.isChecked = keyBool[position]
            (holder as RecyclerViewAdapterViewHolder2).switchKey.setOnCheckedChangeListener{buttonView, isChecked ->
                if(keyBool[position] != isChecked){
                    keyBool[position] = isChecked
                    setupKeys()
                }
            }
        }else{
            (holder as RecyclerViewAdapterViewHolder).bind(arrayList.get(position - keys.size))
            val date = arrayList.get(position - keys.size)
            if(!date.isBooked || !date.isExpired){
                (holder as RecyclerViewAdapterViewHolder).parentItem.setBackgroundColor(context.resources.getColor(R.color.gray))
            }else{
                (holder as RecyclerViewAdapterViewHolder).parentItem.setBackgroundColor(context.resources.getColor(R.color.white))
            }
        }
    }

    class RecyclerViewAdapterViewHolder2(view: View) : RecyclerView.ViewHolder(view) {

        val key: TextView = view.key
        val slots: TextView = view.slots
        val switchKey: SwitchMaterial = view.switch_key

        fun bind(str: String, dates: HashMap<String, ArrayList<Dates>>) {
            key.text = str
            slots.text = "${dates.get(str)?.size} SLOTS AVAILABLE"
        }
    }

    class RecyclerViewAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val endtime: TextView = view.endtime
        val parentItem: LinearLayout = view.parent_item

        fun bind(date: Dates) {
            endtime.text =  getTime(date.startString) + "  -  " + getTime(date.endString)
        }

        fun getTime(date: Date): String{
            val sdf = SimpleDateFormat("HH:mm:ss")
            return sdf.format(date)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if(position < keys.size){
            return TYPE_HEADER
        }else{
            return TYPE_SLOT
        }
    }

    fun setupKeys(){
        arrayList.clear()
        keys.forEachIndexed { index, s ->
            if(keyBool[index]){
                if(dates[s] != null){
                    for (value in dates[s]!!){
                        arrayList.add(value)
                    }
                }
            }
        }
        notifyDataSetChanged()
    }

}