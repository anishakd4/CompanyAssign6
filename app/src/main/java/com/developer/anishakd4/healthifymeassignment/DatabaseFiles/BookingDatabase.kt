package com.developer.anishakd4.healthifymeassignment.DatabaseFiles

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.developer.anishakd4.healthifymeassignment.Model.BookingInfo

@Database(entities = [BookingInfo::class], version = 1, exportSchema = false)
abstract class BookingDatabase: RoomDatabase(){

    abstract val slotsDao: SlotsDao

    companion object{

        @Volatile
        private var INSTANCE: BookingDatabase? = null

        fun getInstance(context: Context):BookingDatabase {
            var instance = INSTANCE

            if(instance == null){
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookingDatabase::class.java,
                    "booking_history_database").fallbackToDestructiveMigration().build()

                INSTANCE = instance
            }

            return instance
        }
    }

}