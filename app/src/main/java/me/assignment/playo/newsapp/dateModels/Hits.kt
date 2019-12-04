package me.assignment.playo.newsapp.dateModels

import androidx.recyclerview.widget.DiffUtil

data class Hits(
    val objectID: String,
    val author: String,
    val title: String,
    val url: String,
    val created_at: String
) {
    companion object {
        val HITS_DIFF_UTIL = object : DiffUtil.ItemCallback<Hits>() {
            override fun areItemsTheSame(oldItem: Hits, newItem: Hits) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Hits, newItem: Hits) =
                oldItem.objectID == newItem.objectID
        }
    }

}