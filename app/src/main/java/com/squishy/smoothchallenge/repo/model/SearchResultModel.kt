package com.squishy.smoothchallenge.repo.model

import android.graphics.ColorSpace

data class SearchResultModel(var id: Int, var title: String, var hex: String, var url: String, var imageUrl: String, var favourite: Boolean = false)