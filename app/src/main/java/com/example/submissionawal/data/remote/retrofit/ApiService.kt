package com.example.submissionawal.data.remote.retrofit

import com.example.submissionawal.data.remote.response.DetailEventResponse
import com.example.submissionawal.data.remote.response.ListEventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    fun getListEvents(
        @Query("active") active: Int? = null,
        @Query("q") q: String? = null
    ): Call<ListEventResponse>

    @GET("events/{id}")
    fun getDetailEvent(
        @Path("id") id: Int
    ): Call<DetailEventResponse>
}
