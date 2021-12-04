package com.example.kinoproisk

import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kinoproisk.databinding.FilmItemBinding

class FilmViewHolder(private val itemBinding: FilmItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
    private val title = itemBinding.title
    private val poster = itemBinding.poster
    private val description = itemBinding.description
    private val ratingDonut = itemBinding.ratingDonut



    fun bind(film: Film){
        title.text = film.title
        Glide.with(itemView)
            .load(film.poster)
            .centerCrop()
            .into(poster)
        description.text = film.description
        ratingDonut.setProgress((film.rating*10).toInt())

    }
}