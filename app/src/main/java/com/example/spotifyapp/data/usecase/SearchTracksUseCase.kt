package com.example.spotifyapp.data.usecase

import com.example.spotifyapp.data.SpotifyRepositoryImpl
import javax.inject.Inject

class SearchTracksUseCase @Inject constructor(private val repo : SpotifyRepositoryImpl) {
    operator fun invoke(token : String, query : String) = repo.searchTracks(token,query)
}