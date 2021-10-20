package com.developer.anishakd4.healthifymeassignment.View

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.developer.anishakd4.healthifymeassignment.DatabaseFiles.SlotsDao

class AcitivityViewModelFactory(private val dataSource: SlotsDao) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ActivityViewModel::class.java)) {
            return ActivityViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}