package com.example.spotifyapp.domain.di

import com.example.spotifyapp.data.network.SpotifyApi
import com.example.spotifyapp.spotifyservice.SpotifyService
import com.example.spotifyapp.util.Constants
import com.spotify.android.appremote.api.SpotifyAppRemote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SpotifyModule {

    @Provides
    @Singleton
    fun provideSpotifyService() : SpotifyService {
        return SpotifyService()
    }

    @Provides
    @Singleton
    fun provideSpotifyAppRemote(service : SpotifyService) : SpotifyAppRemote? {
        return service.mSpotifyAppRemote
    }

    @Provides
    @Singleton
    fun provideHttpLoggerInterceptor() : HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor()
        logger.setLevel(HttpLoggingInterceptor.Level.BODY)
        return logger
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor : HttpLoggingInterceptor) : OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun provideApi(client : OkHttpClient) : SpotifyApi = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(SpotifyApi::class.java)
}