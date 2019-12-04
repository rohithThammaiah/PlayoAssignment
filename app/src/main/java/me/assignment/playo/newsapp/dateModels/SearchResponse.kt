package me.assignment.playo.newsapp.dateModels


data class SearchResponse(
    val exhaustiveNbHits: Boolean,
    val hits: List<Hits>,
    val hitsPerPage: Int,
    val nbHits: Int,
    val nbPages: Int,
    val page: Int,
    val params: String,
    val processingTimeMS: Int,
    val query: String
)