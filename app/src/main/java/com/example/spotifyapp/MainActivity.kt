package com.example.spotifyapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.spotifyapp.databinding.ActivityMainBinding
import com.example.spotifyapp.presentation.SharedViewModel
import com.example.spotifyapp.presentation.ui.home.HomeFragment
import com.example.spotifyapp.spotifyservice.SpotifyService
import com.example.spotifyapp.util.Constants
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val CLIENT_ID = "37ccc8f162d348ffab269c1fc3e78d89"
    private val  REDIRECT_URI = "com.example.spotifyapp://callback"
    private lateinit var binding : ActivityMainBinding
    private val sharedViewModel : SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Constants.REQUEST_CODE) {
            val response = AuthorizationClient.getResponse(resultCode,data)
            when(response.type){
                AuthorizationResponse.Type.TOKEN -> {
                    val token = response.accessToken
                    println(token)
                    putTokenToSharedPreferences(token)
                    sharedViewModel.setToken("Bearer "+token)
                    sharedViewModel.isLoggedIn.value = true

                }
                AuthorizationResponse.Type.ERROR -> {
                    Log.d("Error",response.error.toString())
                }
                else -> {

                }
            }
        }
    }

    fun putTokenToSharedPreferences(token : String){
        val editor = this.getSharedPreferences("Token", MODE_PRIVATE).edit()
        editor.clear()
        editor.apply()
        editor.putString("token",token)
        editor.apply()
    }



}