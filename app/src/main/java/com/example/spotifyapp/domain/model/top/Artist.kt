package com.example.spotifyapp.domain.model.top

import com.example.spotifyapp.domain.model.ExternalUrls

data class Artist(
    val external_urls: ExternalUrls,
    val href: String,
    val id: String,
    val name: String,
    val type: String,
    val uri: String
)