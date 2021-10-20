package com.developer.anishakd4.healthifymeassignment.Model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Dates(val startString: Date,  val endString: Date, val isBooked: Boolean, val isExpired: Boolean) : Parcelable