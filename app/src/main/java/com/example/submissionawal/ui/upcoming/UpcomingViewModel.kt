package com.example.submissionawal.ui.upcoming

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionawal.data.remote.response.ListEventResponse
import com.example.submissionawal.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingViewModel : ViewModel() {

    private val _listEvent = MutableLiveData<ListEventResponse>()
    val listEvent: LiveData<ListEventResponse> = _listEvent

    private val _isLoadingUpcoming = MutableLiveData<Boolean>()
    val isLoadingUpcoming: LiveData<Boolean> = _isLoadingUpcoming

    companion object {
        private const val TAG = "UpcomingViewModel"
        private const val ACTIVE = 1
    }

    private fun findAllEvent() {
        _isLoadingUpcoming.value = true
        val client = ApiConfig.getApiService().getListEvents(ACTIVE)
        client.enqueue(object : Callback<ListEventResponse> {
            override fun onResponse(
                call: Call<ListEventResponse>,
                response: Response<ListEventResponse>
            ) {
                _isLoadingUpcoming.value = false
                if (response.isSuccessful) {
                    _listEvent.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ListEventResponse>, t: Throwable) {
                _isLoadingUpcoming.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    init {
        findAllEvent()
    }
}