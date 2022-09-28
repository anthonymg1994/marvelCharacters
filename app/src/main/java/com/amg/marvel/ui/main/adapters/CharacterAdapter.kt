package com.amg.marvel.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amg.domain.model.character.Character
import com.amg.marvel.databinding.SingleCharacterItemBinding

class CharacterAdapter(val items: List<Character>, private val listener: (Character) -> Unit) :  RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = SingleCharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }
    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    override fun getItemCount(): Int = items.size

    class CharacterViewHolder(private val binding: SingleCharacterItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(character: Character, listener: (Character) -> Unit){
            binding.model = character
            binding.cardCharacter.setOnClickListener {
               listener(character)
            }
        }
    }
}