package com.developer.anishakd4.healthifymeassignment.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Slots")
data class BookingInfo (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "end_time")
    val end_time: String,

    @ColumnInfo(name = "is_booked")
    val is_booked: Boolean,

    @ColumnInfo(name = "is_expired")
    val is_expired: Boolean,

    @ColumnInfo(name = "slot_id")
    val slot_id: Int,

    @ColumnInfo(name = "start_time")
    val start_time: String
)