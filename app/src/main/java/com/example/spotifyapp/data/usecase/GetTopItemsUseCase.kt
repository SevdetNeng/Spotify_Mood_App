package com.example.spotifyapp.data.usecase

import com.example.spotifyapp.domain.model.local.NetworkResponse
import com.example.spotifyapp.data.SpotifyRepositoryImpl
import com.example.spotifyapp.domain.model.top.TopItems
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopItemsUseCase @Inject constructor(private val repo : SpotifyRepositoryImpl) {
    operator fun invoke(token : String,type : String,limit : Int,timeRange : String) : Flow<NetworkResponse<TopItems>> {
        return repo.getTopItems(token,type,limit,timeRange)
    }
}