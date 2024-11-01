package com.example.moviessample.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    internal abstract fun bindTraktRepository(traktRepository: TraktRepositoryImpl): TraktRepository

    @Binds
    internal abstract fun bindTmdbRepository(tmdbRepository: TmdbRepositoryImpl): TmdbRepository

}
