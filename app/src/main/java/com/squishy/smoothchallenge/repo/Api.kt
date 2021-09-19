package com.squishy.smoothchallenge.repo

import com.squishy.smoothchallenge.repo.model.SearchResultModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("colors")
    fun search(
        @Query("keywords") keyboard: String,
        @Query("resultOffset") offset: Int = 0,
        @Query("format") format: String = "json",
        @Query("numResults") num: String = "20"
    ) : Call<List<SearchResultModel>>
}