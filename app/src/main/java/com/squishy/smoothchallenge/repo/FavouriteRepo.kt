package com.squishy.smoothchallenge.repo

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.squishy.smoothchallenge.repo.model.FavouriteModel
import org.json.JSONObject

object FavouriteRepo {

    //todo: SHOULD BE ROOM DB, QUICK HACK

    var sharedPreferences: SharedPreferences? = null
    var map: HashMap<String, Any> = hashMapOf()
    var array: ArrayList<String> = arrayListOf()

    fun init(ctx: Context) {
        sharedPreferences = ctx.getSharedPreferences("SHARED_COLORS", Context.MODE_PRIVATE)
        buildList()
    }

    fun buildList() {
        try {

            val str = sharedPreferences?.getString("list", "")
            if (!str.isNullOrEmpty()) {
                val edit = str.substring(1, str.length - 1)
                Log.e("edit", edit)
                val split = edit.split(", ")
                split.forEach {
                    array.add(it)
                }
                Log.e("FOUND", array.toString())
            }


        } catch (e: Exception) {
            Log.e("TAG", e.localizedMessage)
        }
    }

    fun updateList(hex: String, favourite: Boolean) {

        array.forEach {
            if (it == hex) {
                array.remove(it)
                Log.e(hex, "Removed")
                return
            }
        }

        array.add(hex)
        Log.e(hex, "Added")

        Log.e("TAG", array.toString())
        sharedPreferences?.edit()?.putString("list", array.toString())?.apply()
    }

    fun hasColor(hex: String) : Boolean {
        return array.indexOf(hex) != -1
    }

}