package com.zawmoehtike.movie_compose_multimodule_cleanarch.features.cinemaseat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CinemaSeatPage(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color.Blue)
            .fillMaxSize(),
    )
}