package com.example.spotifyapp.presentation

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotifyapp.domain.model.local.ResponseStatus
import com.example.spotifyapp.data.SpotifyRepositoryImpl
import com.example.spotifyapp.data.usecase.GetTopItemsUseCase
import com.example.spotifyapp.data.usecase.GetUserUseCase
import com.example.spotifyapp.domain.model.top.TopItems
import com.example.spotifyapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val repo : SpotifyRepositoryImpl,
                                          private val getUserUseCase: GetUserUseCase,
                                          private val getTopItemsUseCase: GetTopItemsUseCase
    ) : ViewModel() {

    private var _topTracks : MutableStateFlow<TopItems> = MutableStateFlow(TopItems())
    val topTracks : StateFlow<TopItems> = _topTracks

    private var _topArtists : MutableStateFlow<TopItems> = MutableStateFlow(TopItems())
    val topArtists : StateFlow<TopItems> = _topArtists

    private var _token : MutableStateFlow<String> = MutableStateFlow("")
    val token : StateFlow<String> = _token
    val isLoggedIn : MutableStateFlow<Boolean> = MutableStateFlow(false)

    val isDataParsed : MutableStateFlow<Boolean> = MutableStateFlow(false)

    var isArtistsFetched = false
    var isTracksFetched = false

    fun authorizeUser(activity : Activity){
        repo.authorizeUser(activity)
    }


    fun getTopTracks(){
        viewModelScope.launch {
            getTopItemsUseCase.invoke(token.value,"tracks",10, Constants.TimeRanges.long_term.name).collect(){
                when(it.status){
                    ResponseStatus.SUCCESS -> {
                        println(it.data)
                        it.data?.let { items ->
                            println(items)
                            _topTracks.value = items
                            isTracksFetched = true
                            if(isArtistsFetched){
                                isDataParsed.value = true
                            }
                        }
                    }
                    ResponseStatus.LOADING -> {

                    }
                    else -> {
                        Log.e("Top Error",it.message.toString())
                    }
                }
            }
        }
    }

    fun getTopArtists(){
        viewModelScope.launch {
            getTopItemsUseCase.invoke(token.value,"artists",10, Constants.TimeRanges.long_term.name).collect(){
                when(it.status){
                    ResponseStatus.SUCCESS -> {
                        println(it.data)
                        it.data?.let { items ->
                            println(items)
                            _topArtists.value = items
                            isArtistsFetched = true
                            if(isTracksFetched){
                                isDataParsed.value = true
                            }
                        }
                    }
                    ResponseStatus.LOADING -> {
                    }
                    else -> {
                        Log.e("Top Error",it.message.toString())
                    }
                }
            }
        }
    }

    fun setToken(token : String){
        _token.value = token
    }

}