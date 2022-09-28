package com.amg.marvel.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amg.domain.model.comics.Comic
import com.amg.marvel.databinding.SingleComicItemBinding

class ComicsAdapter (val items: List<Comic>) :  RecyclerView.Adapter<ComicsAdapter.ComicViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val binding = SingleComicItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ComicViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ComicViewHolder(private val binding: SingleComicItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(comic: Comic){
            binding.model = comic
        }
    }
}