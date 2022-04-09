package com.example.kinoproisk.view.rv_adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.example.kinoproisk.R
import com.example.kinoproisk.data.Entity.Film
import com.example.kinoproisk.view.rv_viewholders.FilmViewHolder

class WatchLaterAdapter(private val clickListener: OnItemClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val items = mutableListOf<Film>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FilmViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.film_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FilmViewHolder -> {
                holder.bind(items[position])
                holder.itemView.setOnClickListener {
                    clickListener.click(items[position])
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size

    fun addItems(list: List<Film>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun click(film: Film)
    }

}



