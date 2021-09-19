package com.squishy.smoothchallenge.ui.main

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squishy.smoothchallenge.R
import com.squishy.smoothchallenge.repo.Api
import com.squishy.smoothchallenge.repo.Retro
import com.squishy.smoothchallenge.repo.RetroRepo
import com.squishy.smoothchallenge.repo.model.SearchResultModel
import com.squishy.smoothchallenge.repo.status.ApiResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), TextWatcher, onCellInteraction {

    lateinit var viewmodel: MainViewModel

    lateinit var grid: RecyclerView
    lateinit var search: Button
    lateinit var searchParameter: EditText
    lateinit var colorAdapter: MainColorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        grid = findViewById(R.id.main_rcy_grid)
        search = findViewById(R.id.main_btn_search)
        searchParameter = findViewById(R.id.main_edt_search)
        viewmodel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        grid.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!grid.canScrollVertically(1)) {
                    viewmodel.searchMoreFor(colorAdapter.items.size)
                }
            }
        })

        searchParameter.addTextChangedListener(this)
        search.setOnClickListener {
            viewmodel.searchFor()
        }

        viewmodel.validSearch.observe(this, {
            search.isEnabled = it
        })
        viewmodel.apiResults.observe(this@MainActivity, {
            if (!viewmodel.more) {
                colorAdapter = MainColorAdapter(it, this)
                grid.apply {
                    layoutManager =
                        GridLayoutManager(this@MainActivity, 2, LinearLayoutManager.VERTICAL, false)
                    setHasFixedSize(true)
                    adapter = colorAdapter
                }
            } else {
                colorAdapter.items = it
                colorAdapter.notifyDataSetChanged()
            }

            viewmodel.more = false
        })

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        viewmodel.search.postValue(s.toString())
    }

    override fun afterTextChanged(s: Editable?) {

    }

    override fun onCellPressed(url: String) {
        var intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onFavouriteClicked(position: Int, hex: String, set: Boolean) {
        viewmodel.modifyFavourite(position, hex, set)
    }
}