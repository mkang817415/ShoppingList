package com.example.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shoppinglist.navigation.MainNavigation
import com.example.shoppinglist.ui.screen.categories.CategoriesScreen
import com.example.shoppinglist.ui.screen.shopping.ShoppingListScreen
import com.example.shoppinglist.ui.screen.splash.SplashScreen
import com.example.shoppinglist.ui.screen.summary.SummaryScreen
import com.example.shoppinglist.ui.theme.ShoppingListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingListTheme {

                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomAppBar(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Button(
                                    onClick = {
                                        navController.navigate(MainNavigation.ShoppingListScreen.route)
                                    }
                                ){Text(stringResource(R.string.shopping))}

                                Button(
                                    onClick = {
                                        navController.navigate(MainNavigation.CategoriesScreen.route)
                                    }
                                ){Text(stringResource(R.string.categories))}

                                Button(
                                    onClick = {
                                        navController.navigate(MainNavigation.SummaryScreen.route)
                                    }
                                ){Text(stringResource(R.string.summary))}
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()

                ) { innerPadding ->
                    ShoppingListNavHost(modifier = Modifier.padding(innerPadding), navController = navController)
                }
            }
        }
    }
}



@Composable
fun ShoppingListNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainNavigation.SplashScreen.route
) {
    NavHost(navController = navController, startDestination = startDestination)
    {
        composable(MainNavigation.ShoppingListScreen.route) { ShoppingListScreen() }
        composable(MainNavigation.CategoriesScreen.route){CategoriesScreen()}
        composable(MainNavigation.SummaryScreen.route){SummaryScreen()}
        composable(MainNavigation.SplashScreen.route){
            SplashScreen(onTimeout = {
                navController.navigate(MainNavigation.ShoppingListScreen.route)
            })
        }
    }
}

