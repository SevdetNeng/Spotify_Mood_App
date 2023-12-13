package com.example.spotifyapp.domain.model.search

import com.example.spotifyapp.domain.model.ExternalUrls
import com.example.spotifyapp.domain.model.Followers
import com.example.spotifyapp.domain.model.Image

data class ArtistItem(
    val external_urls: ExternalUrls? = null,
    val followers: Followers? = null,
    val genres: List<String>? = null,
    val href: String? = null,
    val id: String? = null,
    val images: List<Image>? = null,
    val name: String? = null,
    val popularity: Int? = null,
    val type: String? = null,
    val uri: String? = null
)