package com.example.spotifyapp.presentation.ui.home

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotifyapp.domain.model.local.ResponseStatus
import com.example.spotifyapp.data.SpotifyRepositoryImpl
import com.example.spotifyapp.data.usecase.GetRecommendedTracksUseCase
import com.example.spotifyapp.data.usecase.GetTopItemsUseCase
import com.example.spotifyapp.data.usecase.GetUserUseCase
import com.example.spotifyapp.data.usecase.SearchArtistsUseCase
import com.example.spotifyapp.data.usecase.SearchTracksUseCase
import com.example.spotifyapp.domain.model.local.SearchItem
import com.example.spotifyapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.spotifyapp.util.Mapper

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: SpotifyRepositoryImpl,
    private val getUserUseCase: GetUserUseCase,
    private val getRecommendedTracksUseCase: GetRecommendedTracksUseCase,
    private val getTopItemsUseCase: GetTopItemsUseCase,
    private val searchArtistsUseCase: SearchArtistsUseCase,
    private val searchTracksUseCase: SearchTracksUseCase
) : ViewModel() {
    private val _searchedArtists : MutableStateFlow<List<SearchItem>> = MutableStateFlow(emptyList())
    val searchArtists : StateFlow<List<SearchItem>> = _searchedArtists

    private val _searchedTracks : MutableStateFlow<List<SearchItem>> = MutableStateFlow(emptyList())
    val searchTracks : StateFlow<List<SearchItem>> = _searchedTracks

    fun connectToSpotify(context: Context) {
        repo.connectToSpotify(context)
    }

    fun authorizeUser(activity: Activity) {
        repo.authorizeUser(activity)
    }

    fun getUser(token: String) {
        viewModelScope.launch {
            getUserUseCase.invoke(token).collect {
                when (it.status) {
                    ResponseStatus.LOADING -> {

                    }

                    ResponseStatus.SUCCESS -> {
                        println(it.data)
                    }

                    else -> {

                    }
                }
            }
        }

    }

    fun searchArtists(token : String,query : String){
        viewModelScope.launch {
            searchArtistsUseCase.invoke(token,query).collect(){ response ->
                when(response.status){
                    ResponseStatus.LOADING -> {
                        //Loading
                    }
                    ResponseStatus.SUCCESS -> {
                        val list : MutableList<SearchItem> = mutableListOf()
                        response.data?.artists?.items!!.forEach{
                            val searchItem = Mapper.mapArtistToSearchItem(it)
                            list.add(searchItem)
                        }
                        _searchedArtists.value = list
                    }
                    else -> {
                        Log.d("Error",response.message.toString())
                    }
                }

            }
        }
    }

    fun searchTracks(token : String,query: String){
        viewModelScope.launch {
            searchTracksUseCase.invoke(token,query).collect(){ response ->
                when(response.status){
                    ResponseStatus.LOADING -> {
                        //Loading
                    }
                    ResponseStatus.SUCCESS -> {
                        val list : MutableList<SearchItem> = mutableListOf()
                        response.data?.tracks!!.items!!.forEach {
                            val searchItem = Mapper.mapTrackToSearchItem(it)
                            list.add(searchItem)
                        }
                        _searchedTracks.value = list
                    }
                    else -> {
                        Log.d("Error",response.message.toString())
                    }
                }

            }
        }
    }

    fun getTopItems(token: String) {
        viewModelScope.launch {
            getTopItemsUseCase.invoke(token, "tracks", 10, Constants.TimeRanges.long_term.name)
                .collect() {
                    when (it.status) {
                        ResponseStatus.SUCCESS -> {
                            println(it.data)
                        }

                        ResponseStatus.LOADING -> {

                        }

                        else -> {
                            Log.e("Top Error", it.message.toString())
                        }
                    }
                }
        }
    }
}