package com.developer.anishakd4.healthifymeassignment.DatabaseFiles

import androidx.lifecycle.LiveData
import androidx.room.*
import com.developer.anishakd4.healthifymeassignment.Model.BookingInfo

@Dao
interface SlotsDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(night: BookingInfo)

    @Query("SELECT * FROM Slots")
    fun getAllNights(): LiveData<List<BookingInfo>>

}