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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.demo.sharedtransitionanimation.navigation.ScreenRoutes
import com.demo.sharedtransitionanimation.ui.theme.SharedTransitionAnimationTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SharedTransitionAnimationTheme {
                Surface {
                    val navController = rememberNavController()
                    SharedTransitionLayout() {
                        NavHost(
                            navController = navController, startDestination = ScreenRoutes.FoodList
                        ) {
                            composable<ScreenRoutes.FoodList> {
                                FoodListCompose(animatedContentScope = this) {
                                    navController.navigate(
                                        ScreenRoutes.FoodDetails(
                                            name = it.name, desc = it.desc, image = it.image
                                        )
                                    )
                                }
                            }
                            composable<ScreenRoutes.FoodDetails> { backStackEntry ->
                                val data = backStackEntry.toRoute<ScreenRoutes.FoodDetails>()
                                val food = Food(
                                    name = data.name, desc = data.desc, image = data.image
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