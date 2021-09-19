package com.squishy.smoothchallenge.ui.main

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.squishy.smoothchallenge.R
import com.squishy.smoothchallenge.repo.model.SearchResultModel

class MainColorAdapter(var items: ArrayList<SearchResultModel>, var delegate: onCellInteraction) : RecyclerView.Adapter<MainColorAdapter.ViewHolder>() {

    class ViewHolder(var view: View, var delegate: onCellInteraction): RecyclerView.ViewHolder(view) {

        var title: TextView = view.findViewById(R.id.cell_color_title)
        var hex: TextView = view.findViewById(R.id.cell_color_hex)
        var back: ImageView = view.findViewById(R.id.cell_color_back)
        var favourite: ImageView = view.findViewById(R.id.cell_color_favourite)

        fun bind(color: SearchResultModel, position: Int) {
            title.text = color.title
            hex.text = color.hex

            Glide.with(view).load(color.imageUrl).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(back)
            //back.setBackgroundColor(Color.parseColor("#${color.hex}"))

            if (color.favourite) {
                favourite.setImageDrawable(ResourcesCompat.getDrawable(view.context.resources, R.drawable.favourite, null))
            } else {
                favourite.setImageDrawable(ResourcesCompat.getDrawable(view.context.resources, R.drawable.favourite_empty, null))
            }

            back.setOnClickListener {

                delegate.onCellPressed(color.hex)

            }

            favourite.setOnClickListener {

                delegate.onFavouriteClicked(position, color.hex, !color.favourite)
                if (color.favourite) {
                    favourite.setImageDrawable(ResourcesCompat.getDrawable(view.context.resources, R.drawable.favourite, null))
                } else {
                    favourite.setImageDrawable(ResourcesCompat.getDrawable(view.context.resources, R.drawable.favourite_empty, null))
                }

            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cell_color, parent, false), delegate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

}