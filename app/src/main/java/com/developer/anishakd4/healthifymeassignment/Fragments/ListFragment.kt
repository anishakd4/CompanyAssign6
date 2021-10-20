package com.developer.anishakd4.healthifymeassignment.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.developer.anishakd4.healthifymeassignment.Adapters.RecyclerViewAdapter
import com.developer.anishakd4.healthifymeassignment.Model.BookingInfo
import com.developer.anishakd4.healthifymeassignment.Model.Dates
import com.developer.anishakd4.healthifymeassignment.R
import com.developer.anishakd4.healthifymeassignment.databinding.ListFragmentBinding

class ListFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: ListFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false)

        val bundle = arguments

        if(bundle != null){
            val dates: HashMap<String, ArrayList<Dates>> = bundle.getSerializable("list") as HashMap<String, ArrayList<Dates>>

            var arrayKeys: ArrayList<String> = ArrayList<String>()
            for ((k, v) in dates) {
                arrayKeys.add(k)
            }

            val adapter = RecyclerViewAdapter(arrayKeys, dates, context!!)
            binding.list.layoutManager = LinearLayoutManager(context)
            binding.list.adapter = adapter
        }

        return binding.root
    }

}