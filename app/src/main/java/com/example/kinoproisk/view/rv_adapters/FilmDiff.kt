package com.example.kinoproisk.view.rv_adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.kinoproisk.data.Entity.Film

class FilmDiff(val oldList: List<Film>, val newList: List<Film>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition]  == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areItemsTheSame(oldItemPosition, newItemPosition)
    }

}