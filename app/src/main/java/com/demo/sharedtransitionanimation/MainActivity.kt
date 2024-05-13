package com.demo.sharedtransitionanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.demo.sharedtransitionanimation.ui.theme.SharedTransitionAnimationTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SharedTransitionAnimationTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(title = { Text(text = "Food List") })
                    }
                ) { padding ->
                    val navController = rememberNavController()
                    SharedTransitionLayout(modifier = Modifier.padding(padding)) {
                        NavHost(navController = navController, startDestination = "FoodList") {
                            composable("FoodList") {
                                FoodListCompose(animatedContentScope = this) {
                                    navController.navigate("details/${it.name}/${it.desc}/${it.image}")
                                }
                            }
                            composable(
                                route = "details/{name}/{desc}/{image}",
                                arguments = listOf(navArgument("name") {
                                    type = NavType.StringType
                                }, navArgument("desc") {
                                    type = NavType.IntType
                                }, navArgument("image") {
                                    type = NavType.IntType
                                })
                            ) {
                                val food = Food(
                                    name = it.arguments?.getString("name") ?: "",
                                    desc = it.arguments?.getInt("desc") ?: 0,
                                    image = it.arguments?.getInt("image") ?: 0
                                )
                                DetailsScreen(food = food, animatedContentScope = this)
                            }
                        }
                    }
                }

            }
        }
    }


}