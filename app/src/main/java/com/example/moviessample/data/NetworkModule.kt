package com.example.moviessample.data

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val TRAKT_BASE_URL = "https://api.trakt.tv/"

    private const val TMDB_BASE_URL = "https://api.themoviedb.org/3/"

    @Singleton
    @Provides
    fun provideOkhttpClient(@ApplicationContext context: Context) : OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .cache(Cache(context.cacheDir, (5 * 1024 * 1024).toLong()))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectionPool(ConnectionPool(0, 1, TimeUnit.SECONDS))
            .build()
    }

    @Singleton
    @Provides
    fun provideTmdbApiService(okHttpClient: OkHttpClient): TmdbApiService {
        return Retrofit.Builder()
            .baseUrl(TMDB_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TmdbApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideTraktApiService(okHttpClient: OkHttpClient): TraktApiService {
        return Retrofit.Builder()
            .baseUrl(TRAKT_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TraktApiService::class.java)
    }
}
