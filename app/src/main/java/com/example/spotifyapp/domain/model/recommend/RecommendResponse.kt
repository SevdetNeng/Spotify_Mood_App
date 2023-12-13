package com.example.spotifyapp.domain.model.recommend

data class RecommendResponse(
    val seeds: List<Seed>,
    val tracks: List<Track>
)