package com.squishy.smoothchallenge.repo.model

import com.google.gson.annotations.SerializedName

data class FavouriteModel(@SerializedName("hex") var hex: String, @SerializedName("favourite") val favourite: Boolean)