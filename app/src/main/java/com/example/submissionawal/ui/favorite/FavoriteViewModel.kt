package com.example.submissionawal.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissionawal.data.FavoriteRepository
import com.example.submissionawal.data.local.entity.FavoriteEvent
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: FavoriteRepository) : ViewModel() {


//    fun getFavoriteEvents(): LiveData<List<FavoriteEvent>> {
//        return repository.getAllFavoriteEvents()
//    }

    private val _favoriteEvents = MutableLiveData<List<FavoriteEvent>>()
    val favoriteEvents:LiveData<List<FavoriteEvent>> = _favoriteEvents

    fun getFavorites() {
        // Amati LiveData dari repository
        repository.getAllFavoriteEvents().observeForever { favoriteEventList ->
            _favoriteEvents.postValue(favoriteEventList)
        }
    }

//    fun getFavorites() = viewModelScope.launch {
//            val favoriteEvent = repository.getAllFavoriteEvents().value
//            _favoriteEvents.postValue(favoriteEvent)
//    }

    fun isFavorite(eventId: Int): LiveData<Boolean>{
        return repository.isFavorite(eventId)
    }

    fun insert(event: FavoriteEvent) {
        viewModelScope.launch {
            repository.insertFavorite(event)
        }
    }

    fun delete(event: FavoriteEvent) {
        viewModelScope.launch {
            repository.deleteFavorite(event)
        }
    }
}