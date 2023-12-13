package com.example.spotifyapp.data.network

import com.example.spotifyapp.domain.model.User
import com.example.spotifyapp.domain.model.recommend.RecommendResponse
import com.example.spotifyapp.domain.model.search.SearchArtist
import com.example.spotifyapp.domain.model.search.SearchTrack
import com.example.spotifyapp.domain.model.top.Artist
import com.example.spotifyapp.domain.model.top.TopItems
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface SpotifyApi {

    @GET("me")
    @Headers("Authorization: Bearer {token}")
    fun getUser(@Header("Authorization") token: String): Call<User>

    @GET("me/top/{type}")
    fun getTopItems(
        @Header("Authorization") token: String,
        @Path("type") type: String,
        @Query("time_range") timeRange: String,
        @Query("limit") limit: Int
    ): Call<TopItems>

    @GET("recommendations")
    fun getRecommendedTracks(
        @Header("Authorization") token: String,
        @Query("seed_artists") seedArtists: String,
        @Query("seed_tracks") seedTracks: String,
        @Query("target_danceability") danceability: Float,
        @Query("target_energy") energy: Float,
        @Query("target_valence") valence: Float
    ): Call<RecommendResponse>

    @GET("search")
    fun searchArtists(
        @Header("Authorization") token: String,
        @Query("q") query: String,
        @Query("limit") limit: Int = 3,
        @Query("market") market : String = "US",
        @Query("type") type: String = "artist"
    ): Call<SearchArtist>


    @GET("search")
    fun searchTracks(
        @Header("Authorization") token: String,
        @Query("q") query: String,
        @Query("limit") limit: Int = 3,
        @Query("market") market : String = "US",
        @Query("type") type: String = "track"
    ): Call<SearchTrack>
}