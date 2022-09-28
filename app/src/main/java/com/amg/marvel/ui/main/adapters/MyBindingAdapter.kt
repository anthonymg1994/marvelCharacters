package com.amg.marvel.ui.main.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun ImageView.loadImageFromUrl(url: String?){
    Glide.with(this)
        .load(url)
        .fitCenter()
        .into(this);
}