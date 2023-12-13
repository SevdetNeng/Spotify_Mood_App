package com.example.spotifyapp.data.usecase

import com.example.spotifyapp.domain.model.local.NetworkResponse
import com.example.spotifyapp.data.SpotifyRepositoryImpl
import com.example.spotifyapp.domain.model.search.SearchArtist
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchArtistsUseCase @Inject constructor(private val repo : SpotifyRepositoryImpl) {
    operator fun invoke(token : String,query : String) : Flow<NetworkResponse<SearchArtist>> {
        return repo.searchArtists(token,query)
    }
}