package com.zawmoehtike.movie_compose_multimodule_cleanarch.features.moviedetails

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zawmoehtike.base.exceptionmapper.ExceptionHandler
import com.zawmoehtike.base.viewstates.ObjViewState
import com.zawmoehtike.domain.models.*
import com.zawmoehtike.domain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

/**
 * Created by P.T.H.W on 08/04/2024.
 */

@HiltViewModel
class MovieDetailPageViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val handler: ExceptionHandler,
    private val getMovieDetailUseCase: GetMovieDetailUseCase
) : ViewModel() {

    val backdropPath: String = URLDecoder.decode(
        checkNotNull(savedStateHandle["backdropPath"]),
        StandardCharsets.UTF_8.toString()
    )
    val movieId: String = checkNotNull(savedStateHandle["movieId"])

    var movieDetails = mutableStateOf<ObjViewState<MovieDetailVo>>(ObjViewState.Idle())
        private set

    init {
        getMovieDetails(movieId)
    }

    private fun getMovieDetails(movieId: String) {
        viewModelScope.launch {
            movieDetails.value = ObjViewState.Loading()
            runCatching {
                val details = getMovieDetailUseCase.execute(movieId)
                movieDetails.value = ObjViewState.Success(details)
            }.getOrElse {
                Timber.e(it)
                movieDetails.value = ObjViewState.Error(handler.map(it))
            }
        }
    }

}