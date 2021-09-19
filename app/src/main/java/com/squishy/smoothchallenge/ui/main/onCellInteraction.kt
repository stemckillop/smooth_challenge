package com.squishy.smoothchallenge.ui.main

interface onCellInteraction {
    fun onCellPressed(url: String)
    fun onFavouriteClicked(position: Int, hex: String, set: Boolean)
}