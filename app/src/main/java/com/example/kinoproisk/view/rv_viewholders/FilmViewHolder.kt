package com.example.kinoproisk.view.rv_viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kinoproisk.data.ApiConstants
import com.example.kinoproisk.databinding.FilmItemBinding
import com.example.kinoproisk.data.Entity.Film
import com.example.kinoproisk.domain.API

class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val filmItemBinding = FilmItemBinding.bind(itemView)

    private val title = filmItemBinding.title
    private val poster = filmItemBinding.poster
    private val description = filmItemBinding.description
    private val ratingDonut = filmItemBinding.ratingDonut

    fun bind(film: Film) {
        title.text = film.title
        Glide.with(itemView)
            .load(ApiConstants.IMAGES_URL + "w342" + film.poster)
            .centerCrop()
            .into(poster)
        description.text = film.description
        ratingDonut.setProgress((film.rating * 10).toInt())
    }
}