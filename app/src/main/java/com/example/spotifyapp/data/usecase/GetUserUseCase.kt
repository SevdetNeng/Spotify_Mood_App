package com.example.spotifyapp.data.usecase

import com.example.spotifyapp.domain.model.local.NetworkResponse
import com.example.spotifyapp.data.SpotifyRepositoryImpl
import com.example.spotifyapp.domain.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase@Inject constructor(private val repo : SpotifyRepositoryImpl) {
    operator fun invoke(token : String) : Flow<NetworkResponse<User>> {
        return repo.getUser(token)
    }
}