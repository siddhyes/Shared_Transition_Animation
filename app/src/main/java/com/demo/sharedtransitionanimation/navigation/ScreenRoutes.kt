package com.demo.sharedtransitionanimation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.serialization.Serializable

@Serializable
sealed class ScreenRoutes {
    @Serializable
    data object FoodList : ScreenRoutes()
    @Serializable
    data class FoodDetails(
        val name: String,
        @DrawableRes val image: Int,
        @StringRes val desc: Int
    ) :ScreenRoutes()
}