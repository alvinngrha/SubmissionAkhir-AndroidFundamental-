package com.example.submissionawal.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionawal.data.remote.response.ListEventResponse
import com.example.submissionawal.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _searchList = MutableLiveData<ListEventResponse?>()
    val searchList: LiveData<ListEventResponse?> = _searchList

    private val _listEventUpcoming = MutableLiveData<ListEventResponse>()
    val listEventUpcoming: LiveData<ListEventResponse> = _listEventUpcoming

    private val _listEventFinished = MutableLiveData<ListEventResponse>()
    val listEventFinished: LiveData<ListEventResponse> = _listEventFinished

    private val _upcomingIsLoading = MutableLiveData<Boolean>()
    val upcomingIsLoading: LiveData<Boolean> = _upcomingIsLoading

    private val _finishedIsLoading = MutableLiveData<Boolean>()
    val finishedIsLoading: LiveData<Boolean> = _finishedIsLoading

    companion object {
        private const val TAG = "HomeViewModel"
    }

    fun searchEvents(query: String) {
        _upcomingIsLoading.value = true
        val client = ApiConfig.getApiService().getListEvents(active = -1, q = query)
        client.enqueue(object : Callback<ListEventResponse> {
            override fun onResponse(
                call: Call<ListEventResponse>,
                response: Response<ListEventResponse>
            ) {
                _upcomingIsLoading.value = false
                if (response.isSuccessful) {
                    _searchList.value = response.body()
                }
            }

            override fun onFailure(call: Call<ListEventResponse>, t: Throwable) {
                _upcomingIsLoading.value = false
            }
        })
    }

        // Memanggil API untuk mendapatkan event upcoming (active = 1)
        fun getUpcomingEvents() {
            _upcomingIsLoading.value = true
            val client = ApiConfig.getApiService().getListEvents() // Upcoming event
            client.enqueue(object : Callback<ListEventResponse> {
                override fun onResponse(
                    call: Call<ListEventResponse>,
                    response: Response<ListEventResponse>
                ) {
                    _upcomingIsLoading.value = false
                    if (response.isSuccessful) {
                        _listEventUpcoming.value = response.body()
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ListEventResponse>, t: Throwable) {
                    _upcomingIsLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        }

        // Memanggil API untuk mendapatkan event finished (active = 0)
        fun getFinishedEvents() {
            _finishedIsLoading.value = true
            val client = ApiConfig.getApiService().getListEvents(active = 0) // Finished event
            client.enqueue(object : Callback<ListEventResponse> {
                override fun onResponse(
                    call: Call<ListEventResponse>,
                    response: Response<ListEventResponse>
                ) {
                    _finishedIsLoading.value = false
                    if (response.isSuccessful) {
                        _listEventFinished.value = response.body()
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ListEventResponse>, t: Throwable) {
                    _finishedIsLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        }
//    fun clearSearchResults() {
//        _searchList.value = null
//    }

}
