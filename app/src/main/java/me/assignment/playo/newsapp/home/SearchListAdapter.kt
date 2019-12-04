package me.assignment.playo.newsapp.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.home_search_list_item.view.*
import me.assignment.playo.newsapp.R
import me.assignment.playo.newsapp.dateModels.Hits
import me.assignment.playo.newsapp.recyclerview.OnRVItemClickListener

class SearchListAdapter(
    private val onSearchItemClickListener: OnRVItemClickListener<Hits>
) : ListAdapter<Hits, SearchListAdapter.SearchViewHolder>(Hits.HITS_DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val vw = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_search_list_item, parent, false)
        return SearchViewHolder(vw)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        getItem(position)?.let { hits ->
            with(holder.snippetView) {
                search_list_item_name.text = hits.title
                search_list_item_description.text = hits.created_at
                setOnClickListener {
                    onSearchItemClickListener.onClick(hits, this)
                }
            }
        }
    }

    class SearchViewHolder(val snippetView: View) : RecyclerView.ViewHolder(snippetView)
}