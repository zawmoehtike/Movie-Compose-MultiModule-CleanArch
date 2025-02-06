package com.zawmoehtike.base.di

import com.zawmoehtike.base.exceptionmapper.*
import com.zawmoehtike.base.utils.DefaultDispatcherProvider
import com.zawmoehtike.domain.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by P.T.H.W on 11/03/2024.
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class BaseAppModule {
    @Binds
    abstract fun exceptionMapper(exceptionMapperImpl: ExceptionHandlerImpl): ExceptionHandler

    @Binds
    abstract fun dispatcherProvider(dispatcherProvider: DefaultDispatcherProvider): DispatcherProvider
}