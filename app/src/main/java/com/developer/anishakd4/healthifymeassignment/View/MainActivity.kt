package com.developer.anishakd4.healthifymeassignment.View

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.developer.anishakd4.healthifymeassignment.Adapters.ViewPagerAdapter
import com.developer.anishakd4.healthifymeassignment.DatabaseFiles.BookingDatabase
import com.developer.anishakd4.healthifymeassignment.Model.Dates
import com.developer.anishakd4.healthifymeassignment.R
import com.developer.anishakd4.healthifymeassignment.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import kotlinx.android.synthetic.main.list_item_header.*


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: ActivityViewModel
    lateinit var binding: ActivityMainBinding
    lateinit var viewPagerAdpater: ViewPagerAdapter

    var hashMap = HashMap<Int, HashMap<String, java.util.ArrayList<Dates>>>()
    var keysList = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val dataSource = BookingDatabase.getInstance(application).slotsDao
        val viewModelFactory = AcitivityViewModelFactory(dataSource)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ActivityViewModel::class.java)
        viewModel.refresh()

        val toolbar: Toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

//        viewModel = ViewModelProviders.of(this).get(ActivityViewModel::class.java)
//        viewModel.refresh()

        observeViewModel()
    }

    fun observeViewModel() {

        viewModel.bookingInfos2.observe(this, Observer {
            viewModel.insertIntoDb()
        })

        viewModel.bookingInfos.observe(this, Observer {
            viewModel.convertToDates()
        })

        viewModel.hashMap.observe(this, Observer {

            binding.viewPager.visibility = View.VISIBLE
            binding.tabs.visibility = View.VISIBLE

            hashMap = it
            for ((k, v) in hashMap){
                keysList.add(k)
            }

            viewPagerAdpater = ViewPagerAdapter(this, keysList, hashMap)
            binding.viewPager.adapter = viewPagerAdpater
            binding.viewPager.offscreenPageLimit = 1

            val tabLayoutMediator = TabLayoutMediator(binding.tabs, binding.viewPager,
                TabConfigurationStrategy { tab, position ->
                    tab.text = keysList.get(position).toString()
                    binding.viewPager.currentItem = position
                })

            tabLayoutMediator.attach()

            binding.viewPager.currentItem = 0
        })

        viewModel.loadError.observe(this, Observer { isError ->
            binding.listError.visibility = if (isError == "" || isError == null) View.GONE else View.VISIBLE
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                binding.loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    binding.listError.visibility = View.GONE
                    binding.viewPager.visibility = View.GONE
                    binding.tabs.visibility = View.GONE
                }
            }
        })
    }
}
