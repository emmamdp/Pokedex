package com.emdp.features.home.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.emdp.core.ui.R
import com.emdp.core.ui.components.apptopbar.PokedexAppTopBarImage
import com.emdp.core.ui.components.background.BackgroundPokeball
import com.emdp.core.ui.components.background.pokedexBackgroundGradient
import com.emdp.core.ui.components.card.PokedexClickableCard
import com.emdp.core.ui.textstyle.brand.BrandTitle
import com.emdp.core.ui.textstyle.brand.BrandTitleSize
import com.emdp.core.ui.theme.PkOnPrimaryWhite
import com.emdp.features.home.presentation.common.PokedexHomeButtonDestinations.PokemonList
import com.emdp.features.home.presentation.common.homeCards
import com.emdp.features.home.presentation.uimodel.HomeCardUiModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PokedexHomeScreen(onOpenPokemonList: () -> Unit) {

    Scaffold(
        topBar = {
            PokedexAppTopBarImage(
                backgroundRes = R.drawable.pk_top_bar,
                height = 120.dp,
                backgroundColor = Color.Transparent,
                contentScale = ContentScale.FillWidth
            )
        }
    ) { innerPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .background(pokedexBackgroundGradient())
                .padding(innerPadding)
        ) {
            BackgroundPokeball(modifier = Modifier.matchParentSize())

            Column(modifier = Modifier.fillMaxSize()) {
                BrandTitle(
                    text = stringResource(R.string.pokedex_home_title),
                    size = BrandTitleSize.L,
                    color = PkOnPrimaryWhite,
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 20.dp),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(4.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(homeCards) { card ->
                        HomeCard(
                            data = card,
                            onClick = {
                                if (card.enabled && card.destination == PokemonList) onOpenPokemonList()
                            },
                            modifier = Modifier.height(60.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeCard(
    data: HomeCardUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    PokedexClickableCard(
        modifier = modifier,
        onClick = onClick,
        isButtonClickable = data.enabled
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.horizontalGradient(colors = data.gradient))
                .padding(start = 18.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
        ) {
            Image(
                painter = painterResource(id = data.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(40.dp)
            )
            Text(
                text = stringResource(data.title),
                style = MaterialTheme.typography.titleSmall,
                color = PkOnPrimaryWhite,
                textAlign = TextAlign.Start,
                modifier = Modifier.align(Alignment.BottomStart)
            )
        }
    }
}