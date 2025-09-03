package com.emdp.features.splash.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.emdp.core.common.base.presentation.PokedexBaseState
import com.emdp.core.navigation.PokedexDestination.OpenPokemonList
import com.emdp.core.ui.R
import com.emdp.core.ui.components.progressbar.OrbitingSparkProgress
import com.emdp.core.ui.textstyle.brand.BrandTitle
import com.emdp.core.ui.textstyle.brand.BrandTitleSize
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PokedexSplashScreen(
    navigateToHomeScreen: () -> Unit,
    viewModel: PokedexSplashViewModel = koinViewModel()
) {
    val state = viewModel.screenState.collectAsState().value

    LaunchedEffect(Unit) {
        delay(1200)
        viewModel.initialize()
    }

    when (state) {
        is PokedexBaseState.Loading -> EpicSplashScreen()
        is PokedexBaseState.NavigateToNextView -> {
            if (state.destination is OpenPokemonList) {
                LaunchedEffect(state) { navigateToHomeScreen() }
            }
        }

        else -> Unit
    }
}

@Composable
fun EpicSplashScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.pokemon_splash_epic),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OrbitingSparkProgress(
                modifier = Modifier.size(50.dp),
                strokeWidth = 6.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
            BrandTitle(
                text = stringResource(R.string.splash_loading),
                size = BrandTitleSize.S,
                textAlign = TextAlign.Center
            )
        }
    }
}