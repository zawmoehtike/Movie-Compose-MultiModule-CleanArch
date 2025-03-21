package com.zawmoehtike.movie_compose_multimodule_cleanarch.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.zawmoehtike.movie_compose_multimodule_cleanarch.R
import com.zawmoehtike.movie_compose_multimodule_cleanarch.ui.theme.ColorPrimary
import com.zawmoehtike.movie_compose_multimodule_cleanarch.ui.theme.ComposeMovieAppCleanArchitectureTheme
import com.zawmoehtike.movie_compose_multimodule_cleanarch.ui.theme.Dimens
import com.zawmoehtike.movie_compose_multimodule_cleanarch.ui.theme.Shapes

/**
 * Created by P.T.H.W on 02/04/2024.
 */

@Composable
fun SectionTitleWithSeeAll(
    modifier: Modifier = Modifier,
    title: String,
    onSeeAll: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = Dimens.MARGIN_MEDIUM_2),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TitleTextView(text = title)
        Row(
            modifier = Modifier
                .clip(Shapes.small)
                .clickable {
                    onSeeAll()
                }
                .padding(Dimens.MARGIN_MEDIUM),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "See All",
                color = ColorPrimary,
                fontSize = Dimens.TEXT_REGULAR
            )
            Spacer(modifier = Modifier.padding(end = Dimens.MARGIN_SMALL))
            Icon(
                modifier = Modifier
                    .size(Dimens.MARGIN_20)
                    .rotate(180f),
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = "",
                tint = ColorPrimary,
            )
            Spacer(modifier = Modifier.padding(end = Dimens.MARGIN_MEDIUM))
        }
    }
}

@Preview
@Composable
private fun SectionTitleWithSeeAllPreview() {
    ComposeMovieAppCleanArchitectureTheme {
        Surface {
            SectionTitleWithSeeAll(modifier = Modifier, title = "Test Title")
        }
    }
}
