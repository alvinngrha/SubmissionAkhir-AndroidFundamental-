package com.example.submissionawal.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.submissionawal.data.local.entity.FavoriteEvent

@Dao
interface FavoriteEventDao {


    // Untuk mendapatkan semua data favorite
    @Query("SELECT * FROM favorite_event")
    fun getFavorite(): LiveData<List<FavoriteEvent>>

//    @Query("SELECT * FROM favorite_event where favorite = 1")
//    fun getFavoriteEvent(): LiveData<List<FavoriteEvent>>

    // Menambahkan beberapa data event favorit ke database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEvents(events: FavoriteEvent)

    // Mengupdate data event favorit di database
    @Update
    fun updateEvent(event: FavoriteEvent)

    // Menghapus data event favorit dari database
    @Delete
    suspend fun deleteEvent(event: FavoriteEvent)

    // Mendapatkan event favorit berdasarkan ID
    @Query("SELECT EXISTS(SELECT 1 FROM favorite_event WHERE id = :id AND favorite = 1)")
    fun isFavorite(id: Int): LiveData<Boolean>
}