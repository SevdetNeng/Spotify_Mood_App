package com.example.spotifyapp.spotifyservice

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.spotifyapp.MainActivity
import com.example.spotifyapp.util.Constants
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import com.spotify.sdk.android.auth.app.SpotifyAuthHandler

class SpotifyService() {

    var mSpotifyAppRemote : SpotifyAppRemote?  = null
    private var connectionParams : ConnectionParams = ConnectionParams.Builder(Constants.CLIENT_ID)
        .setRedirectUri(Constants.REDIRECT_URI)
        .showAuthView(true)
        .build()

    fun connect(context: Context) {


        val connectionListener = object : Connector.ConnectionListener {
            override fun onConnected(spotifyAppRemote: SpotifyAppRemote) {
                mSpotifyAppRemote = spotifyAppRemote

            }

            override fun onFailure(throwable: Throwable) {
                Log.e("SpotifyService", throwable.message, throwable)
            }
        }
        SpotifyAppRemote.connect(context, connectionParams, connectionListener)

    }
    fun authorizeUser(activity : Activity){
        val builder = AuthorizationRequest.Builder(
            Constants.CLIENT_ID,
            AuthorizationResponse.Type.TOKEN,
            Constants.REDIRECT_URI
        )
        builder.setScopes(arrayOf("streaming",
            "app-remote-control",
            "user-top-read",
            "playlist-modify-public",
            "playlist-modify-private"))
        val request = builder.build()
        AuthorizationClient.openLoginActivity(activity,Constants.REQUEST_CODE,request)

    }
}