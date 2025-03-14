package com.zawmoehtike.movie_compose_multimodule_cleanarch.features.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zawmoehtike.base.exceptionmapper.ExceptionHandler
import com.zawmoehtike.base.viewstates.ObjViewState
import com.zawmoehtike.domain.models.*
import com.zawmoehtike.domain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by P.T.H.W on 02/04/2024.
 */

@HiltViewModel
class HomeNavPageViewModel @Inject constructor(
    private val handler: ExceptionHandler,
    private val fetchHomeDataUseCase: FetchHomeDataUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val getUpComingMoviesUseCase: GetUpComingMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getPopularPeopleUseCase: GetPopularPeopleUseCase,
    private val getMovieGenresUseCase: GetMovieGenresUseCase
) : ViewModel() {

    var refreshing = mutableStateOf(false)
        private set
    var nowPlayingMovies = mutableStateOf<ObjViewState<List<MovieVo>>>(ObjViewState.Idle())
        private set
    var upComingMovies = mutableStateOf<ObjViewState<List<MovieVo>>>(ObjViewState.Idle())
        private set
    var popularMovies = mutableStateOf<ObjViewState<List<MovieVo>>>(ObjViewState.Idle())
        private set
    var popularPeople = mutableStateOf<ObjViewState<List<ActorVo>>>(ObjViewState.Idle())
        private set


    init {
        // from database
        getNowPlayingMovies()
        getUpComingMovies()
        getPopularMovies()
        getPopularPeople()

        // from network
        fetchHomeData()
        fetchMovieGenres()
    }

    private fun fetchHomeData() {
        viewModelScope.launch {
            runCatching {
                fetchHomeDataUseCase.execute(Unit)
            }.getOrElse {
                Timber.e(it)
            }
        }
    }

    private fun fetchMovieGenres() {
        viewModelScope.launch {
            runCatching {
                getMovieGenresUseCase.execute(Unit)
            }.getOrElse {
                Timber.e(it)
            }
        }
    }

    private fun getNowPlayingMovies() {
        viewModelScope.launch {
            getNowPlayingMoviesUseCase.execute(Unit).collectLatest {
                if (it.isNotEmpty()) {
                    delay(200)
                    nowPlayingMovies.value = ObjViewState.Success(it)
                }
            }
        }
    }

    private fun getUpComingMovies() {
        viewModelScope.launch {
            getUpComingMoviesUseCase.execute(Unit).collectLatest {
                upComingMovies.value = ObjViewState.Success(it)
            }
        }
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            getPopularMoviesUseCase.execute(Unit).collectLatest {
                popularMovies.value = ObjViewState.Success(it)
            }
        }
    }

    private fun getPopularPeople() {
        viewModelScope.launch {
            getPopularPeopleUseCase.execute(Unit).collectLatest {
                popularPeople.value = ObjViewState.Success(it)
            }
        }
    }

    fun refreshHomeData() {
        viewModelScope.launch {
            refreshing.value = true
            delay(1000)
            fetchHomeData()
            refreshing.value = false
        }
    }
}