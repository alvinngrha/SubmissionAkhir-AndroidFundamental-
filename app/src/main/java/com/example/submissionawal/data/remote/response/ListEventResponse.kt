package com.example.submissionawal.data.remote.response

import com.google.gson.annotations.SerializedName

data class ListEventResponse(

    @field:SerializedName("listEvents")
	val listEvents: List<Event>,

    @field:SerializedName("error")
	val error: Boolean,

    @field:SerializedName("message")
	val message: String
)

