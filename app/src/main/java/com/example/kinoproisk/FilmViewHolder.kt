package com.example.kinoproisk

import androidx.recyclerview.widget.RecyclerView
import com.example.kinoproisk.databinding.FilmItemBinding

class FilmViewHolder(private val itemBinding: FilmItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
    private val title = itemBinding.title
    private val poster = itemBinding.poster
    private val description = itemBinding.description


    fun bind(film: Film){
        title.text = film.title
        poster.setImageResource(film.poster)
        description.text = film.description
    }
}