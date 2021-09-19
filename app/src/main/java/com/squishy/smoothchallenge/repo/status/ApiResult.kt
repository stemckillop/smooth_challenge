package com.squishy.smoothchallenge.repo.status

interface ApiResult {
    fun <T> onSuccess(data: T)
    fun <T> onFailure(data: T)
}