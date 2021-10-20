package com.developer.anishakd4.healthifymeassignment.Adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.developer.anishakd4.healthifymeassignment.Fragments.ListFragment
import com.developer.anishakd4.healthifymeassignment.Model.BookingInfo
import com.developer.anishakd4.healthifymeassignment.Model.Dates

class ViewPagerAdapter(fragmentActivity: FragmentActivity,
                       val keysList: ArrayList<Int>,
                       val hashMap: HashMap<Int, HashMap<String, ArrayList<Dates>>>) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return keysList.size;
    }

    override fun createFragment(position: Int): Fragment {
        val args = Bundle()
        var listFragment: ListFragment = ListFragment()
        args.putSerializable("list", hashMap.get(keysList.get(position)));
        listFragment.arguments = args
        return listFragment
    }

}