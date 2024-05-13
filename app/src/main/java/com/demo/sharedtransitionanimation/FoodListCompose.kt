package com.demo.sharedtransitionanimation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.FoodListCompose(
    animatedContentScope: AnimatedVisibilityScope,
    onItemClick: (Food) -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Food List") })
    }) {padding->
        LazyColumn(
            modifier = Modifier.padding(padding)
                .fillMaxSize()
                .padding(20.dp)
        ) {
            itemsIndexed(foodList) { i, item ->

                AnimatedBorderCard(
                    modifier = Modifier.padding(top = 15.dp),
                    shape = RoundedCornerShape(5.dp),
                    borderWidth=1.dp,
                    gradient = Brush.sweepGradient(listOf(Color.Red, Color.Cyan,Color.Blue)),


                    ) {
                    Row(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .clickable {
                                onItemClick.invoke(item)
                            }, verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1.0f)) {
                            Text(
                                text = item.name,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.Companion.sharedElement(
                                    state = rememberSharedContentState(key = "name-${item.name}"),
                                    animatedVisibilityScope = animatedContentScope
                                )
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = stringResource(id = item.desc),
                                style = MaterialTheme.typography.bodyMedium,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 4,
                                modifier = Modifier.Companion.sharedElement(
                                    state = rememberSharedContentState(key = "desc-${item.desc}"),
                                    animatedVisibilityScope = animatedContentScope
                                )
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                        }
                        Image(
                            painter = painterResource(id = item.image),
                            contentDescription = "",
                            modifier = Modifier
                                .weight(.29f)
                                .fillMaxHeight()
                                .sharedElement(
                                    state = rememberSharedContentState(key = "image-${item.image}"),
                                    animatedVisibilityScope = animatedContentScope
                                )
                        )
                    }
                }


            }
        }
    }
}

data class Food(
    val name: String,
    @DrawableRes val image: Int,
    @StringRes val desc: Int
)

val foodList = listOf(
    Food(
        name = "Italian Pizza",
        image = R.drawable.pizza_1,
        desc = R.string.italian_pizza
    ),
    Food(
        name = "Mexican Pizza",
        image = R.drawable.pizza_2,
        desc = R.string.mexican_pizza
    ),
    Food(
        name = "Cheese Burger",
        image = R.drawable.burger,
        desc = R.string.cheese_burger
    ),
)