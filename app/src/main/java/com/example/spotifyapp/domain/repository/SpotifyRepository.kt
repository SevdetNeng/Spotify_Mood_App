package com.example.spotifyapp.domain.repository

import android.app.Activity
import android.content.Context
import com.example.spotifyapp.domain.model.local.NetworkResponse
import com.example.spotifyapp.domain.model.User
import com.example.spotifyapp.domain.model.recommend.RecommendResponse
import com.example.spotifyapp.domain.model.search.SearchArtist
import com.example.spotifyapp.domain.model.search.SearchTrack
import com.example.spotifyapp.domain.model.top.TopItems
import kotlinx.coroutines.flow.Flow

interface SpotifyRepository  {
    fun connectToSpotify(context : Context)
    
    fun authorizeUser(activity : Activity)

    fun getUser(token : String) : Flow<NetworkResponse<User>>

    fun getTopItems(token : String,type : String,limit : Int,timeRange : String) : Flow<NetworkResponse<TopItems>>

    fun getRecommendedTracks(seedArtists : String, seedTracks : String,token: String,danceability : Float,energy : Float,valence : Float) : Flow<NetworkResponse<RecommendResponse>>

    fun searchArtists(token : String, query : String) : Flow<NetworkResponse<SearchArtist>>

    fun searchTracks(token : String, query : String) : Flow<NetworkResponse<SearchTrack>>
}