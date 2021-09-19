package com.squishy.smoothchallenge.repo

import android.util.Log
import com.squishy.smoothchallenge.repo.model.SearchResultModel
import com.squishy.smoothchallenge.repo.status.ApiResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RetroRepo {

    fun searchColor(search: String, result: ApiResult) {
        Retro.API.search(search).enqueue(object: Callback<List<SearchResultModel>> {
            override fun onResponse(
                call: Call<List<SearchResultModel>>,
                response: Response<List<SearchResultModel>>
            ) {
                if (response.isSuccessful) {
                    result.onSuccess(response.body()!!)
                } else {
                    result.onFailure(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<List<SearchResultModel>>, t: Throwable) {
                result.onFailure(t.localizedMessage)
            }

        })
    }

    fun searchMoreColor(search: String, num: Int, result: ApiResult) {
        Retro.API.search(search, num).enqueue(object: Callback<List<SearchResultModel>> {
            override fun onResponse(
                call: Call<List<SearchResultModel>>,
                response: Response<List<SearchResultModel>>
            ) {
                if (response.isSuccessful) {
                    result.onSuccess(response.body()!!)
                } else {
                    result.onFailure(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<List<SearchResultModel>>, t: Throwable) {
                result.onFailure(t.localizedMessage)
            }

        })
    }

}