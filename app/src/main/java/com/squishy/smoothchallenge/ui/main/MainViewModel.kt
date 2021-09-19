package com.squishy.smoothchallenge.ui.main

import android.util.Log
import android.view.SearchEvent
import android.view.View
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squishy.smoothchallenge.repo.FavouriteRepo
import com.squishy.smoothchallenge.repo.RetroRepo
import com.squishy.smoothchallenge.repo.model.SearchResultModel
import com.squishy.smoothchallenge.repo.status.ApiResult
import com.squishy.smoothchallenge.repo.status.ApiStatus

class MainViewModel() : ViewModel() {

    var search : MutableLiveData<String> = MutableLiveData()
    var prevSearch : String = ""
    var status: MutableLiveData<ApiStatus> = MutableLiveData()
    var error: MutableLiveData<String> = MutableLiveData()

    var validSearch = MediatorLiveData<Boolean>().apply {
        addSource(search) {
            value = search.value!!.length in 3..10
        }
    }

    var more = false
    var apiResults : MutableLiveData<ArrayList<SearchResultModel>> = MutableLiveData()
    var results : ArrayList<SearchResultModel> = arrayListOf()

    init {
        apiResults.postValue(results)
    }

    fun searchFor() {
        prevSearch = search.value!!
        status.postValue(ApiStatus.LOADING)
        apiResults.value = arrayListOf()
        results.clear()
        RetroRepo.searchColor(prevSearch, object: ApiResult{
            override fun <T> onSuccess(data: T) {
                status.postValue(ApiStatus.SUCCESS)
                Log.e("ViewModel", data.toString())
                more = false
                results.addAll(data as ArrayList<SearchResultModel>)
                results.forEach {
                    if (FavouriteRepo.hasColor(it.hex)) {
                        it.favourite = true
                    }
                }
                apiResults.postValue(results)

            }

            override fun <T> onFailure(data: T) {
                status.postValue(ApiStatus.ERROR)
                Log.e("ViewModel", data.toString())
            }
        })
    }

    fun searchMoreFor(num: Int) {
        if (status.value != ApiStatus.LOADING) {
            status.postValue(ApiStatus.LOADING)
            RetroRepo.searchMoreColor(prevSearch, num, object : ApiResult {
                override fun <T> onSuccess(data: T) {
                    status.postValue(ApiStatus.SUCCESS)
                    Log.e("ViewModel", data.toString())
                    more = true
                    results.addAll(data as ArrayList<SearchResultModel>)
                    results.forEach {
                        if (FavouriteRepo.hasColor(it.hex)) {
                            it.favourite = true
                        }
                    }
                    apiResults.postValue(results)

                }

                override fun <T> onFailure(data: T) {
                    status.postValue(ApiStatus.ERROR)
                    Log.e("ViewModel", data.toString())
                }

            })
        }
    }

    fun modifyFavourite(position: Int, hex: String, favourite: Boolean) {
        FavouriteRepo.updateList(hex, favourite)
        results[position].favourite = favourite
        apiResults.value = results
    }
}