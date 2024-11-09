package com.example.submissionawal.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorite_event")
data class FavoriteEvent(
    @PrimaryKey
    val id: Int,
    val name: String,
    val summary: String?,
    val imageLogo: String?,
    val favorite: Boolean = true  // default true karena ini tabel khusus favorite
)

