package me.assignment.playo.newsapp.recyclerview

import android.view.View

interface OnRVItemClickListener<T> {
    fun onClick(item: T, view: View)
}