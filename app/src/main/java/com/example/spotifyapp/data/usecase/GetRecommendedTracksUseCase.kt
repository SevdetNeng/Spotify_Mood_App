package com.example.spotifyapp.data.usecase

import com.example.spotifyapp.domain.model.local.NetworkResponse
import com.example.spotifyapp.data.SpotifyRepositoryImpl
import com.example.spotifyapp.domain.model.recommend.RecommendResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecommendedTracksUseCase @Inject constructor(private val repo : SpotifyRepositoryImpl) {
    operator fun invoke(seedArtists : String,seedTracks: String, token: String, danceability: Float,
                        energy: Float, valence: Float) : Flow<NetworkResponse<RecommendResponse>> {
        return repo.getRecommendedTracks(seedArtists, seedTracks,token,danceability,energy,valence)
    }
}