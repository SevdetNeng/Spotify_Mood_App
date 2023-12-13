package com.example.spotifyapp.domain.model.top

data class TopItems(
    val href: String? = null,
    val items: List<Item>? = null,
    val limit: Int? = null,
    val next: String? = null,
    val offset: Int? = null,
    val previous: Any? = null,
    val total: Int? = null
)