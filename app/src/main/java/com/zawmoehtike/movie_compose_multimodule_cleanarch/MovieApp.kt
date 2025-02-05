package com.zawmoehtike.movie_compose_multimodule_cleanarch

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by P.T.H.W on 25/03/2024.
 */
@HiltAndroidApp
class MovieApp: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}