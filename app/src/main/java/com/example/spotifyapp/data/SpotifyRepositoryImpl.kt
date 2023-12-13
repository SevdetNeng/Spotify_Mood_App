package com.example.spotifyapp.data

import android.app.Activity
import android.content.Context
import com.example.spotifyapp.data.network.SpotifyApi
import com.example.spotifyapp.domain.repository.SpotifyRepository
import com.example.spotifyapp.domain.model.User
import com.example.spotifyapp.domain.model.local.NetworkResponse
import com.example.spotifyapp.domain.model.recommend.RecommendResponse
import com.example.spotifyapp.domain.model.search.SearchArtist
import com.example.spotifyapp.domain.model.search.SearchTrack
import com.example.spotifyapp.domain.model.top.TopItems
import com.example.spotifyapp.spotifyservice.SpotifyService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SpotifyRepositoryImpl @Inject constructor(
    private val service: SpotifyService,
    private val api: SpotifyApi
) : SpotifyRepository {
    override fun connectToSpotify(context: Context) {

        service.connect(context)
        if (service.mSpotifyAppRemote?.isConnected == true) {
            println("connected")
            service.mSpotifyAppRemote
        }
    }

    override fun authorizeUser(activity: Activity) {
        service.authorizeUser(activity)
    }

    override fun getUser(token: String): Flow<NetworkResponse<User>> {
        return callbackFlow {
            val response = api.getUser(token)
            trySend(NetworkResponse.Loading)
            response.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        body?.let {
                            trySend(NetworkResponse.Success(_data = body))
                        }
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    trySend(NetworkResponse.Error(_message = t.message.toString()))
                }

            })
            awaitClose()
        }
    }

    override fun getTopItems(
        token: String,
        type: String,
        limit: Int,
        timeRange: String
    ): Flow<NetworkResponse<TopItems>> {
        return callbackFlow {
            val response = api.getTopItems(token, type, timeRange, limit)
            trySend(NetworkResponse.Loading)
            response.enqueue(object : Callback<TopItems> {
                override fun onResponse(call: Call<TopItems>, response: Response<TopItems>) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        body?.let {
                            trySend(NetworkResponse.Success(body))
                        }
                    }
                }

                override fun onFailure(call: Call<TopItems>, t: Throwable) {
                    println(t.message.toString())
                    trySend(NetworkResponse.Error(t.message.toString()))
                }

            })
            awaitClose()
        }
    }

    override fun getRecommendedTracks(
        seedTracks: String, token: String, danceability: Float,
        energy: Float, valence: Float
    ): Flow<NetworkResponse<RecommendResponse>> {
        return callbackFlow {
            val response = api.getRecommendedTracks(token,seedTracks,danceability,energy,valence)
            trySend(NetworkResponse.Loading)
            response.enqueue(object : Callback<RecommendResponse>{
                override fun onResponse(
                    call: Call<RecommendResponse>,
                    response: Response<RecommendResponse>
                ) {
                    if(response.isSuccessful){
                        val body = response.body()
                        body?.let {
                            trySend(NetworkResponse.Success(it))
                        }
                    }
                }
                override fun onFailure(call: Call<RecommendResponse>, t: Throwable) {
                    trySend(NetworkResponse.Error(t.message.toString()))
                }
            })
            awaitClose()
        }
    }

    override fun searchArtists(token: String, query : String) : Flow<NetworkResponse<SearchArtist>>{
        return callbackFlow {
            trySend(NetworkResponse.Loading)
            val response = api.searchArtists(token,query)
            response.enqueue(object : Callback<SearchArtist>{
                override fun onResponse(
                    call: Call<SearchArtist>,
                    response: Response<SearchArtist>
                ) {
                    if(response.isSuccessful){
                        val body = response.body()
                        body?.let {
                            trySend(NetworkResponse.Success(it))
                        }
                    }
                    else{
                        trySend(NetworkResponse.Error(response.message()))
                    }
                }

                override fun onFailure(call: Call<SearchArtist>, t: Throwable) {
                        trySend(NetworkResponse.Error(t.message.toString()))
                }
            })
            awaitClose()
        }
    }

    override fun searchTracks(token: String, query: String): Flow<NetworkResponse<SearchTrack>> {
        return callbackFlow {
            trySend(NetworkResponse.Loading)
            val response = api.searchTracks(token,query)
            response.enqueue(object : Callback<SearchTrack>{
                override fun onResponse(
                    call: Call<SearchTrack>,
                    response: Response<SearchTrack>
                ) {
                    if(response.isSuccessful){
                        val body = response.body()
                        body?.let {
                            trySend(NetworkResponse.Success(it))
                        }
                    }
                    else{
                        trySend(NetworkResponse.Error(response.message()))
                    }
                }
                override fun onFailure(call: Call<SearchTrack>, t: Throwable) {
                    trySend(NetworkResponse.Error(t.message.toString()))
                }
            })
            awaitClose()
        }
    }
}