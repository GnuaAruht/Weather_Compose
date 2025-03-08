package com.gnua_aruht.weather_compose.presentation.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.gnua_aruht.weather_compose.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshLayout(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    state: PullToRefreshState = rememberPullToRefreshState(),
    content: @Composable BoxScope.() -> Unit,
) {

    Column(
        modifier = modifier.pullToRefresh(
            state = state,
            isRefreshing = isRefreshing,
            onRefresh = onRefresh
        )
    ) {
        PullToRefreshContent(
            distanceFraction = state.distanceFraction,
            isRefreshing = isRefreshing,
        )
        Box(modifier = Modifier.weight(1f)) {
            content()
        }
    }
}


val maxHeight = 60.dp

@Composable
fun PullToRefreshContent(
    distanceFraction: Float,
    isRefreshing: Boolean,
    modifier: Modifier = Modifier,
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize()
            .height(maxHeight * distanceFraction.coerceIn(0f..1f))
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = isRefreshing,
            label = "indicator_content"
        ) { refreshing ->
            if(refreshing) {
                LoadingIndicator(Modifier.width(120.dp))
            }
            else {
                Text("Last updated ....")
            }
        }
    }
}

@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier,
        contentScale = ContentScale.FillWidth
    )

}