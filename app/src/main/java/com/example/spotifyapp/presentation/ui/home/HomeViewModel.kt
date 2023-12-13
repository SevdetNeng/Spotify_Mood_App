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
import com.example.spotifyapp.domain.model.local.NetworkResponse
import com.example.spotifyapp.domain.model.local.SearchItem
import com.example.spotifyapp.domain.model.recommend.Track
import com.example.spotifyapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.spotifyapp.util.Mapper
import com.example.spotifyapp.util.SearchType
import kotlinx.coroutines.flow.collect

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

    private val _selectedItems : MutableStateFlow<MutableList<SearchItem>> = MutableStateFlow(
        mutableListOf()
    )
    val selectedItems : StateFlow<MutableList<SearchItem>> = _selectedItems

    private val _recommendedTracks : MutableStateFlow<List<Track>> = MutableStateFlow(emptyList())
    val recommendedTracks : StateFlow<List<Track>> = _recommendedTracks

    fun authorizeUser(activity: Activity) {
        repo.authorizeUser(activity)
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

    fun selectItem(item : SearchItem){
        if(_selectedItems.value.size<5){
            val copyList = _selectedItems.value.toMutableList()
            copyList.add(item)
            _selectedItems.value = copyList

        }
    }

    fun unselectItem(item : SearchItem){
        val copyList = _selectedItems.value.toMutableList()
        copyList.remove(item)
        _selectedItems.value = copyList
    }

    fun getArtistSeeds() : String {
        val artists = selectedItems.value.filter {
            it.type == SearchType.ARTIST
        }
        val ids = mutableListOf<String>()
        artists.forEach {
            ids.add(it.id)
        }
        return ids.joinToString(",")
    }
    fun getTrackSeeds() : String {
        val tracks = selectedItems.value.filter {
            it.type == SearchType.TRACK
        }
        val ids = mutableListOf<String>()
        tracks.forEach {
            ids.add(it.id)
        }
        return ids.joinToString(",")
    }

    fun getRecommendedItems(token : String,valence : Float,energy : Float,
                            danceability : Float){
        viewModelScope.launch {
            val artists = getArtistSeeds()
            val tracks = getTrackSeeds()
            getRecommendedTracksUseCase.invoke(artists, tracks, token, danceability, energy, valence).collect(){ response ->

                when(response.status) {
                    ResponseStatus.LOADING -> {
                        //loading
                    }
                    ResponseStatus.SUCCESS -> {
                        val body = response.data
                        body?.let {
                            _recommendedTracks.value = it.tracks
                        }
                    }
                    else -> {
                        //error
                    }
                }

            }
        }
    }
}