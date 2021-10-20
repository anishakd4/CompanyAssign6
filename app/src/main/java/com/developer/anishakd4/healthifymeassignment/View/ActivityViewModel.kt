package com.developer.anishakd4.healthifymeassignment.View

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.developer.anishakd4.healthifymeassignment.DatabaseFiles.SlotsDao
import com.developer.anishakd4.healthifymeassignment.Model.BookingInfo
import com.developer.anishakd4.healthifymeassignment.Model.Dates
import com.developer.anishakd4.healthifymeassignment.Networking.BookingService
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class ActivityViewModel(val database: SlotsDao) : ViewModel() {

    val countriesService = BookingService.getBookingInfoService()

    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception: ${throwable.localizedMessage}")
    }

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var bookingInfos = MutableLiveData<List<BookingInfo>>()
    val loadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    val hashMap = MutableLiveData<HashMap<Int, HashMap<String, ArrayList<Dates>>>>()

    var bookingInfos2: LiveData<List<BookingInfo>>

    init {
        bookingInfos2 = database.getAllNights()
    }

    fun refresh() {
        fetchSlots()
    }

    private fun fetchSlots() {
        loading.value = true

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            withContext(Dispatchers.IO + exceptionHandler){
                val response = countriesService.getCountires()
                withContext(Dispatchers.Main){
                    if(response.isSuccessful){
                        bookingInfos.value = response.body()
                        loadError.value = null
                        loading.value = false
                        //convertToDates()
                    }else{
                        onError("Error ${response.message()}")
                    }
                }
            }
        }
    }

    fun insertIntoDb(){
        uiScope.launch {
            withContext(Dispatchers.IO){
                val value = bookingInfos.value

                if(value != null){
                    for (i in value){
                        database.insert(i)
                    }
                }
            }
        }
    }

    fun convertToDates() {

        var x = hashMap.value
        if(x == null){
            x = HashMap<Int, HashMap<String, ArrayList<Dates>>>()
        }

        val arr = bookingInfos.value

        if(arr != null){
            for (i in arr) {
                val date1: Date? = convertToDate(i.start_time)
                val date2: Date? = convertToDate(i.end_time)
                val isBooked: Boolean = i.is_booked
                val isExpired: Boolean = i.is_expired

                if (date1 != null && date2 != null) {
                    val hours = getHour(date1)
                    if(hours < 4){
                        if (x[date1.date] == null) {
                            x[date1.date] = HashMap<String, ArrayList<Dates>>();
                            val y = x[date1.date]
                            if(y?.get("MORNING") == null){
                                y?.set("MORNING", ArrayList<Dates>())
                                val d: Dates = Dates(date1, date2, isBooked, isExpired)
                                y?.get("MORNING")?.add(d)
                            }else{
                                val d: Dates = Dates(date1, date2, isBooked, isExpired)
                                y?.get("MORNING")?.add(d)
                            }
                        } else {
                            val y = x[date1.date]
                            if(y?.get("MORNING") == null){
                                y?.set("MORNING", ArrayList<Dates>())
                                val d: Dates = Dates(date1, date2, isBooked, isExpired)
                                y?.get("MORNING")?.add(d)
                            }else{
                                val d: Dates = Dates(date1, date2, isBooked, isExpired)
                                y?.get("MORNING")?.add(d)
                            }
                        }
                    }else{
                        if (x[date1.date] == null) {
                            x[date1.date] = HashMap<String, ArrayList<Dates>>();
                            val y = x[date1.date]
                            if(y?.get("EVENING") == null){
                                y?.set("EVENING", ArrayList<Dates>())
                                val d: Dates = Dates(date1, date2, isBooked, isExpired)
                                y?.get("EVENING")?.add(d)
                            }else{
                                val d: Dates = Dates(date1, date2, isBooked, isExpired)
                                y?.get("EVENING")?.add(d)
                            }
                        } else {
                            val y = x[date1.date]
                            if(y?.get("EVENING") == null){
                                y?.set("EVENING", ArrayList<Dates>())
                                val d: Dates = Dates(date1, date2, isBooked, isExpired)
                                y?.get("EVENING")?.add(d)
                            }else{
                                val d: Dates = Dates(date1, date2, isBooked, isExpired)
                                y?.get("EVENING")?.add(d)
                            }
                        }
                    }
                }
            }
        }

        hashMap.value = x
    }

    fun getHour(date: Date): Int{
        val calendar = Calendar.getInstance()
        calendar.time = date
        val hours = calendar[Calendar.HOUR_OF_DAY]
        return hours
    }

    fun convertToDate(str: String): Date? {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var date: Date? = null
        try {
            date = sdf.parse(str)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return date
    }

    private fun onError(message: String) {
        loadError.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
        viewModelJob.cancel()
    }

}