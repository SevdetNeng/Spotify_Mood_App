package com.example.spotifyapp.domain.model.local

import com.example.spotifyapp.util.SearchType

data class SearchItem(
    val title : String,
    val imgUrl : String? = null,
    val id : String,
    val type : SearchType? = null
)
