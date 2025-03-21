package com.zawmoehtike.movie_compose_multimodule_cleanarch.features.payment

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zawmoehtike.movie_compose_multimodule_cleanarch.R
import com.zawmoehtike.movie_compose_multimodule_cleanarch.composable.CoilAsyncImage
import com.zawmoehtike.movie_compose_multimodule_cleanarch.composable.CustomTextField
import com.zawmoehtike.movie_compose_multimodule_cleanarch.composable.TitleTextView
import com.zawmoehtike.movie_compose_multimodule_cleanarch.composable.TopAppBarView
import com.zawmoehtike.movie_compose_multimodule_cleanarch.features.ticket.navigateToInvoiceTicketPage
import com.zawmoehtike.movie_compose_multimodule_cleanarch.ui.theme.ColorCinemaSeatReserved
import com.zawmoehtike.movie_compose_multimodule_cleanarch.ui.theme.ColorPrimary
import com.zawmoehtike.movie_compose_multimodule_cleanarch.ui.theme.ComposeMovieAppCleanArchitectureTheme
import com.zawmoehtike.movie_compose_multimodule_cleanarch.ui.theme.Dimens
import com.zawmoehtike.movie_compose_multimodule_cleanarch.ui.theme.LocalCustomColors
import com.zawmoehtike.movie_compose_multimodule_cleanarch.ui.theme.LocalNavController
import com.zawmoehtike.movie_compose_multimodule_cleanarch.ui.theme.Shapes


@Composable
fun PaymentPage(
    modifier: Modifier = Modifier,
    navController: NavController = LocalNavController.current
) {
    PageContent(modifier = modifier,
        onAction = {
            when (it) {
                UiEvent.GoBack -> navController.popBackStack()
                UiEvent.Continue -> navController.navigateToInvoiceTicketPage()
            }
        }
    )
}

private sealed class UiEvent {
    data object GoBack : UiEvent()
    data object Continue : UiEvent()
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PageContent(
    modifier: Modifier,
    onAction: (UiEvent) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBarView("Payment") {
                onAction(UiEvent.GoBack)
            }
        },
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = Dimens.MARGIN_MEDIUM_2)
        ) {
            item {
                Spacer(modifier = modifier.height(Dimens.MARGIN_MEDIUM_2))
                PaymentMovieInfoCard(
                    modifier
                        .height(100.dp)
                        .fillParentMaxHeight()
                )
                Spacer(modifier = modifier.height(Dimens.MARGIN_MEDIUM_2))

                TicketInfoDesc(title = "Oder ID", text = "78889377726")
                TicketInfoDesc(title = "Seat", text = "H7, H8")

                DiscountCodeApplySection()
                HorizontalDivider(
                    color = Color.DarkGray,
                    modifier = Modifier.padding(top = Dimens.MARGIN_MEDIUM_2)
                )

                Spacer(modifier = modifier.height(Dimens.MARGIN_MEDIUM_2))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Total", fontSize = Dimens.TEXT_REGULAR)
                    Text(
                        text = "210.000 VND",
                        fontSize = Dimens.TEXT_XLARGE,
                        color = ColorPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = modifier.height(Dimens.MARGIN_20))

                // payment method
                TitleTextView(text = "Payment Method")
                Spacer(modifier = modifier.height(Dimens.MARGIN_12))
                FlowRow {
                    for (i in 1..5) {
                        PaymentMethodListItem()
                    }
                }


                Spacer(modifier = modifier.height(Dimens.MARGIN_10))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(Shapes.medium)
                        .background(color = ColorCinemaSeatReserved)
                        .padding(
                            vertical = Dimens.MARGIN_MEDIUM,
                            horizontal = Dimens.MARGIN_MEDIUM_2
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Complete your payment in",
                        fontSize = Dimens.TEXT_REGULAR,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "15:00",
                        fontSize = Dimens.TEXT_REGULAR_2,
                        fontWeight = FontWeight.Bold,
                        color = ColorPrimary
                    )
                }

                Spacer(modifier = modifier.height(Dimens.MARGIN_XXLARGE))

                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = ColorPrimary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimens.BTN_COMMON_HEIGHT),
                    onClick = {
                        onAction(UiEvent.Continue)
                    },
                ) {
                    Text(text = "Continue", fontSize = Dimens.TEXT_REGULAR_2)
                }

                Spacer(modifier = modifier.height(Dimens.MARGIN_XXLARGE))

            }
        }
    }
}

@Composable
private fun PaymentMethodListItem() {
    Row(
        modifier = Modifier
            .padding(bottom = Dimens.MARGIN_12)
            .clip(Shapes.medium)
            .background(color = LocalCustomColors.current.cardBackgroundColor)
            .padding(Dimens.MARGIN_12),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.height(Dimens.MARGIN_XXLARGE),
            painter = painterResource(id = R.drawable.img_visa_master),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.width(Dimens.MARGIN_MEDIUM_2))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "International payments",
                fontSize = Dimens.TEXT_REGULAR
            )
            Text(
                text = "(Visa, Master, JCB, Amex)",
                fontSize = Dimens.TEXT_SMALL
            )
        }
        Icon(
            imageVector = Icons.Rounded.KeyboardArrowRight,
            contentDescription = ""
        )
    }
}

@Composable
private fun DiscountCodeApplySection() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(
                vertical = Dimens.MARGIN_MEDIUM_2
            )
            .clip(Shapes.small)
            .height(IntrinsicSize.Min)
            .background(color = Color.DarkGray)
    ) {
        Spacer(modifier = Modifier.width(Dimens.MARGIN_MEDIUM_2))
        Icon(
            painter = painterResource(id = R.drawable.ic_discount_shape),
            contentDescription = ""
        )
//        TextField(
//            modifier = Modifier
//                .fillMaxWidth()
//                .requiredHeight(40.dp)
//                .weight(1f),
//            value = "", onValueChange = {},
//            textStyle = TextStyle(
//                fontSize = Dimens.TEXT_REGULAR
//            ),
//            colors = TextFieldDefaults.colors(
//                unfocusedContainerColor = Color.Transparent,
//                focusedContainerColor = Color.Transparent,
//                focusedIndicatorColor = Color.Transparent,
//                unfocusedIndicatorColor = Color.Transparent,
//                disabledIndicatorColor = Color.Transparent
//            ),
//            placeholder = {
//                Text(
//                    text = "discount code",
//                    color = Color.Gray,
//                    fontSize = Dimens.TEXT_REGULAR
//                )
//            }
//        )

        CustomTextField(
            modifier = Modifier
                .padding(start = Dimens.MARGIN_MEDIUM)
                .weight(1f),
            placeholderText = "discount code"
        )

        Button(
            modifier = Modifier.height(40.dp),
            shape = Shapes.small,
            contentPadding = PaddingValues(horizontal = Dimens.MARGIN_XLARGE),
            colors = ButtonDefaults.buttonColors(containerColor = ColorPrimary),
            onClick = {
                // todo
            }) {
            Text(
                text = "Apply",
                fontSize = Dimens.TEXT_REGULAR,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun TicketInfoDesc(
    title: String,
    text: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = Dimens.MARGIN_MEDIUM
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, fontSize = Dimens.TEXT_REGULAR)
        Text(
            text = text,
            fontSize = Dimens.TEXT_REGULAR,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun PaymentMovieInfoCard(
    modifier: Modifier
) {
    Row(
        modifier = Modifier
            .clip(Shapes.medium)
            .background(color = LocalCustomColors.current.cardBackgroundColor)
    ) {
        CoilAsyncImage(
            imageUrl = "https://mlpnk72yciwc.i.optimole.com/cqhiHLc.IIZS~2ef73/w:auto/h:auto/q:75/https://bleedingcool.com/wp-content/uploads/2024/01/godzilla_x_kong_the_new_empire_ver5_xxlg.jpg",
            modifier = modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(Dimens.MARGIN_12))

        Column(
            modifier = Modifier
                .weight(2f)
                .padding(vertical = Dimens.MARGIN_MEDIUM_2)
        ) {
            TitleTextView(
                text = "Avengers: Infinity War",
                color = ColorPrimary,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(Dimens.MARGIN_MEDIUM))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = painterResource(id = R.drawable.ic_video_info), "")
                Spacer(modifier = Modifier.width(Dimens.MARGIN_MEDIUM))
                Text(text = "Adventure, Sci-fi", fontSize = Dimens.TEXT_SMALL)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = painterResource(id = R.drawable.ic_video_info), "")
                Spacer(modifier = Modifier.width(Dimens.MARGIN_MEDIUM))
                Text(text = "Vincom Ocean Park CGV", fontSize = Dimens.TEXT_SMALL)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = painterResource(id = R.drawable.ic_video_info), "")
                Spacer(modifier = Modifier.width(Dimens.MARGIN_MEDIUM))
                Text(text = "10.12.2022 • 14:15", fontSize = Dimens.TEXT_SMALL)
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun PageContentPreview() {
    ComposeMovieAppCleanArchitectureTheme {
        Surface {
            PageContent(modifier = Modifier)
        }
    }
}