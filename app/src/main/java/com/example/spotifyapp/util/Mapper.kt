package com.example.spotifyapp.util

import com.example.spotifyapp.domain.model.local.SearchItem
import com.example.spotifyapp.domain.model.search.ArtistItem
import com.example.spotifyapp.domain.model.search.SearchTrack
import com.example.spotifyapp.domain.model.top.Item

object Mapper {
    fun mapArtistToSearchItem(artist : ArtistItem) : SearchItem {
        return SearchItem(
            title = artist.name!!,
            imgUrl = if(artist.images.isNullOrEmpty()) null else artist.images[0].url,
            id = artist.id!!,
            type = SearchType.ARTIST
        )
    }

    fun mapTrackToSearchItem(track : Item) : SearchItem {
        return SearchItem(
            title = track.name!!,
            imgUrl = if(track.album.images.isEmpty()) null else track.album.images[0].url,
            id = track.id!!,
            type = SearchType.TRACK
        )
    }
}