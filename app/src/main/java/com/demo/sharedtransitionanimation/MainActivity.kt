package com.demo.sharedtransitionanimation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import gaur.himanshu.sharedtransitionanimation.R
import com.demo.sharedtransitionanimation.ui.theme.SharedTransitionAnimationTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SharedTransitionAnimationTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(title = { Text(text = "Food List") })
                    }) { innerPadding ->
                   Box(modifier = Modifier.fillMaxSize().padding(innerPadding)){
                       FoodList(modifier = Modifier)
                   }
                    Log.d("TAG", "onCreate: ${innerPadding}")
                }
            }
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    fun FoodList(modifier: Modifier = Modifier) {
        SharedTransitionLayout {
            val isDetailsScreen = remember {
                mutableStateOf(false)
            }
            val index = remember {
                mutableIntStateOf(-1)

            }
            AnimatedContent(targetState = isDetailsScreen.value, label = "") { targetState ->
                if (targetState) {
                    Column(
                        modifier = Modifier
                            .clickable {
                                isDetailsScreen.value = isDetailsScreen.value.not()
                            }
                            .padding(horizontal = 12.dp)
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Image(
                            painter = painterResource(id = foodList.get(index.intValue).image),
                            contentDescription = "",
                            modifier = Modifier
                                .width(300.dp)
                                .height(300.dp).align(Alignment.CenterHorizontally)
                                .sharedElement(
                                    state = rememberSharedContentState(key = "image-${index.intValue}"),
                                    animatedVisibilityScope = this@AnimatedContent
                                )
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = foodList.get(index.intValue).name,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.sharedElement(
                                state = rememberSharedContentState(key = "name-${index.intValue}"),
                                animatedVisibilityScope = this@AnimatedContent
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = stringResource(id = foodList.get(index.intValue).desc),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.sharedElement(
                                state = rememberSharedContentState(key = "desc-${index.intValue}"),
                                animatedVisibilityScope = this@AnimatedContent
                            )
                        )
                    }
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize().padding(20.dp)) {
                        itemsIndexed(foodList) { i, item ->
                            Row(modifier = Modifier.fillMaxWidth()
                                .clickable {
                                    isDetailsScreen.value = isDetailsScreen.value.not()
                                    index.intValue = i
                                }, verticalAlignment = Alignment.CenterVertically) {
                                Column (modifier = Modifier.weight(1.0f)){
                                    Text(
                                        text = item.name,
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier.sharedElement(
                                            state = rememberSharedContentState(key = "name-${i}"),
                                            animatedVisibilityScope = this@AnimatedContent
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        text = stringResource(id = item.desc),
                                        style = MaterialTheme.typography.bodyMedium,
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 4,
                                        modifier = Modifier.sharedElement(
                                            state = rememberSharedContentState(key = "desc-${i}"),
                                            animatedVisibilityScope = this@AnimatedContent
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
                                            state = rememberSharedContentState(key = "image-${i}"),
                                            animatedVisibilityScope = this@AnimatedContent
                                        )
                                )
                            }
                        }
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
        image = R.drawable.pizza_1,
        desc = R.string.mexican_pizza
    ),
    Food(
        name = "Cheese Burger",
        image = R.drawable.burger,
        desc = R.string.cheese_burger
    ),
)
