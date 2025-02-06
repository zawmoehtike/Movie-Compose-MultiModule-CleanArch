package com.zawmoehtike.base.di

import com.zawmoehtike.data.repositories.*
import com.zawmoehtike.domain.repositories.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * Created by P.T.H.W on 02/04/2024.
 */

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindHomeRepository(repositoryImpl: HomeRepositoryImpl): HomeRepository

    @Binds
    abstract fun bindMovieRepository(repositoryImpl: MovieRepositoryImpl): MovieRepository
}