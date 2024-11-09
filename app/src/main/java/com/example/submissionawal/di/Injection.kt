package com.example.submissionawal.di

import android.content.Context
import com.example.submissionawal.data.FavoriteRepository
import com.example.submissionawal.data.local.room.EventDatabase
import com.example.submissionawal.data.remote.retrofit.ApiConfig
import com.example.submissionawal.util.AppExecutors

object Injection {
    fun provideRepository(context: Context): FavoriteRepository {
        val apiService = ApiConfig.getApiService()
        val database = EventDatabase.getInstance(context)
        val dao = database.eventDao()
        val appExecutors = AppExecutors()
        return FavoriteRepository.getInstance(apiService, dao, appExecutors)
    }
}