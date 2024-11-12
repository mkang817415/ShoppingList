package com.example.shoppinglist.navigation

sealed class MainNavigation(val route: String){
    object ShoppingListScreen: MainNavigation("shoppinglist")
    object CategoriesScreen: MainNavigation("categories")
    object SummaryScreen: MainNavigation("summary")
    object SplashScreen: MainNavigation("splash")

}

