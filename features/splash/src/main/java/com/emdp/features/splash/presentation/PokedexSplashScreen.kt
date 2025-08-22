package com.emdp.features.splash.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay

@Composable
fun PokedexSplashScreen(onOpenPokemonList: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(1200)
        onOpenPokemonList()
    }
}