package com.demo.sharedtransitionanimation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.DetailsScreen(
    food: Food,
    animatedContentScope: AnimatedContentScope
) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "${food.name}") })
    }) {padding->
        Column(
            modifier = Modifier.padding(padding)
                .padding(horizontal = 12.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(id = food.image),
                contentDescription = "",
                modifier = Modifier
                    .width(300.dp)
                    .height(300.dp)
                    .align(Alignment.CenterHorizontally)
                    .sharedElement(
                        state = rememberSharedContentState(key = "image-${food.image}"),
                        animatedVisibilityScope = animatedContentScope
                    )
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = food.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.Companion.sharedElement(
                    state = rememberSharedContentState(key = "name-${food.name}"),
                    animatedVisibilityScope = animatedContentScope
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(id = food.desc),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.Companion.sharedElement(
                    state = rememberSharedContentState(key = "desc-${food.desc}"),
                    animatedVisibilityScope = animatedContentScope
                )
            )
        }
    }
}