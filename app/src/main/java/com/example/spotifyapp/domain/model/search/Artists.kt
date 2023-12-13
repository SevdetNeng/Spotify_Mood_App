package com.example.spotifyapp.domain.model.search

data class Artists(
    val href: String,
    val items: List<ArtistItem>,
    val limit: Int,
    val next: String,
    val offset: Int,
    val previous: Any,
    val total: Int
)