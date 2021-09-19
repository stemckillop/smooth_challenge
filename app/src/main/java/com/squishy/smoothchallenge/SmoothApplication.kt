package com.squishy.smoothchallenge

import android.app.Application
import com.squishy.smoothchallenge.repo.FavouriteRepo

class SmoothApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FavouriteRepo.init(this)
    }
}