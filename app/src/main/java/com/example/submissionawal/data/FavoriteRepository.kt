package com.example.submissionawal.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.submissionawal.data.local.entity.FavoriteEvent
import com.example.submissionawal.data.local.room.FavoriteEventDao
import com.example.submissionawal.data.remote.retrofit.ApiService
import com.example.submissionawal.util.AppExecutors

class FavoriteRepository(
    private val apiService: ApiService,
    private val dao: FavoriteEventDao,
    private val appExecutors: AppExecutors) {

    private val result = MediatorLiveData<Result<List<FavoriteEvent>>>()

//    fun getEvents(): LiveData<Result<List<FavoriteEvent>>> {
//        result.value = Result.Loading
//        val client = apiService.getListEvents()
//        client.enqueue(object : Callback<ListEventResponse> {
//            override fun onResponse(call: Call<ListEventResponse>, response: Response<ListEventResponse>) {
//                if (response.isSuccessful) {
//                    val events = response.body()?.listEvents
//                    val eventList = ArrayList<FavoriteEvent>()
//                    appExecutors.diskIO.execute {
//                        events?.forEach { event ->
//                            val isFavorite = dao.isFavorite(event.id)
//                            val newEvents = FavoriteEvent(
//                                event.id,
//                                event.name,
//                                event.summary,
//                                event.imageLogo,
//                                isFavorite = true
//                            )
//                            eventList.add(newEvents)
//                        }
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<ListEventResponse>, t: Throwable) {
//                result.value = Result.Error(t.message.toString())
//            }
//        })
//        val localData = dao.getFavorite()
//        result.addSource(localData) { newData: List<FavoriteEvent> ->
//            result.value = Result.Success(newData)
//        }
//        return result
//    }

    fun getAllFavoriteEvents(): LiveData<List<FavoriteEvent>> = dao.getFavorite()

    fun isFavorite(id: Int): LiveData<Boolean> = dao.isFavorite(id)

    // Fungsi untuk menambah dan menghapus event favorit
    suspend fun insertFavorite(event: FavoriteEvent) {
        dao.insertEvents(event)
    }

    suspend fun deleteFavorite(event: FavoriteEvent) {
        dao.deleteEvent(event)
    }

    suspend fun updateFavorite(event: FavoriteEvent) {
        dao.updateEvent(event)
    }


    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null
        fun getInstance(
            apiService: ApiService,
            dao: FavoriteEventDao,
            appExecutors: AppExecutors
        ): FavoriteRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteRepository(apiService, dao, appExecutors)
            }.also { instance = it }
        }
}