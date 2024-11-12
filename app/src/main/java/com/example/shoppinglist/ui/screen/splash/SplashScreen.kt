package com.example.shoppinglist.ui.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.R

@Composable
fun SplashScreen(modifier: Modifier = Modifier, onTimeout: () -> Unit){
    LaunchedEffect(Unit){
        kotlinx.coroutines.delay(3000)
        onTimeout()
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ){
        Image(
            painter = painterResource(id = R.drawable.pic),
            contentDescription = stringResource(R.string.shopping_intro),
            modifier = Modifier.size(400.dp)
        )
    }
}