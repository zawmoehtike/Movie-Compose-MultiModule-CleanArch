package com.zawmoehtike.movie_compose_multimodule_cleanarch

import android.app.Application
import com.zawmoehtike.share.BuildConfig
import com.zawmoehtike.share.extensions.isInDev
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by P.T.H.W on 25/03/2024.
 */
@HiltAndroidApp
class MovieApp: Application() {
    override fun onCreate() {
        super.onCreate()
        setupTimber()
    }

    private fun setupTimber() {
        if (BuildConfig.BUILD_TYPE.isInDev()) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    return String.format(
                        "C:%s, L:%s",
                        super.createStackElementTag(element)?.substringBefore("$")
                            ?: "Timber",
                        element.lineNumber,
//                        element.methodName
                    )
                }
            })
        }
    }
}