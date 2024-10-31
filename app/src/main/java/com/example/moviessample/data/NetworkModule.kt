package com.example.moviessample.data

import android.content.Context
import com.example.moviessample.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
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
    fun provideTraktClient(@ApplicationContext context: Context) : OkHttpClient {
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
    fun provideTmdbNetworkClient(@ApplicationContext context: Context) : OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(
                object : Interceptor {
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val request: Request = chain.request()
                            .newBuilder()
                            .header("accept", "application/json")
                            .header("Authorization", BuildConfig.TMDB_ACCESS_TOKEN)
                            .build()
                        return chain.proceed(request)
                    }
                }
            )
            .cache(Cache(context.cacheDir, (5 * 1024 * 1024).toLong()))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectionPool(ConnectionPool(0, 1, TimeUnit.SECONDS))
            .build()
    }

    @Singleton
    @Provides
    fun provideTmdbApiService(tmdbNetworkClient: OkHttpClient): TmdbApiService {
        return Retrofit.Builder()
            .baseUrl(TMDB_BASE_URL)
            .client(tmdbNetworkClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TmdbApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideTraktApiService(traktNetworkClient: OkHttpClient): TraktApiService {
        return Retrofit.Builder()
            .baseUrl(TRAKT_BASE_URL)
            .client(traktNetworkClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TraktApiService::class.java)
    }
}
