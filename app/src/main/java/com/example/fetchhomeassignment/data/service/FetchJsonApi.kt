package com.example.fetchhomeassignment.data.service

import com.example.fetchhomeassignment.data.FetchListItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface FetchJsonApi {

    @GET
    fun getFetchItems(
        @Url url: String,
    ): Call<List<FetchListItem>>
}

const val FETCH_JSON_BASE_URL = "https://fetch-hiring.s3.amazonaws.com"
const val FETCH_JSON_ENDPOINT = "$FETCH_JSON_BASE_URL/hiring.json"