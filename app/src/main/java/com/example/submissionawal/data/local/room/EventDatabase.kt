package com.example.submissionawal.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.submissionawal.data.local.entity.FavoriteEvent

@Database(entities = [FavoriteEvent::class], version = 2, exportSchema = false)
abstract class EventDatabase : RoomDatabase(){
    abstract fun eventDao(): FavoriteEventDao

    companion object {
        @Volatile
        private var instance: EventDatabase? = null
        fun getInstance(context: Context): EventDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    EventDatabase::class.java, "Event.db"
                )
                    .fallbackToDestructiveMigration()
                    .build().also { instance = it }
            }
        }
}