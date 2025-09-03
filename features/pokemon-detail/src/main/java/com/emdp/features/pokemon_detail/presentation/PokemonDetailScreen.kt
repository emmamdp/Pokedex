package com.emdp.features.pokemon_detail.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.emdp.core.common.base.presentation.PokedexBaseState
import com.emdp.core.ui.R
import com.emdp.core.ui.components.apptopbar.PokedexAppTopBar
import com.emdp.core.ui.components.background.BackgroundPokeball
import com.emdp.core.ui.components.chip.PokedexTypeChips
import com.emdp.core.ui.components.pill.PokedexInfoPill
import com.emdp.core.ui.components.progressbar.OrbitingSparkProgress
import com.emdp.core.ui.formats.UiFormatters
import com.emdp.core.ui.theme.PkOnPrimaryWhite
import com.emdp.core.ui.theme.PokemonTypeChipColor
import com.emdp.core.ui.theme.PokemonTypeColor
import com.emdp.core.ui.theme.ProgressBarColor
import com.emdp.domain.model.PokemonDetailModel
import com.emdp.domain.model.StatModel
import com.emdp.domain.model.types.PokemonType
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreen(
    id: Int,
    onBackClick: () -> Unit,
    viewModel: PokemonDetailViewModel = koinViewModel()
) {
    LaunchedEffect(id) { viewModel.load(pokemonId = id) }

    val baseState by viewModel.screenState.collectAsState()
    val backgroundColor: Color = getBackgroundColor(baseState = baseState)
    val onColor: Color = PokemonTypeColor.onTextColorFor(background = backgroundColor)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                var isFavorite by rememberSaveable(id) { mutableStateOf(false) }

                PokedexAppTopBar(
                    titleText = when (val state = baseState) {
                        is PokedexBaseState.Content -> state.data.name
                        else -> stringResource(R.string.pokedex_pokemon_detail_pokemon)
                    },
                    textColor = onColor,
                    onBackClick = onBackClick,
                    showFavoriteButton = true,
                    isFavoriteButtonSelected = isFavorite,
                    onToggleFavoriteButton = { isFavorite = !isFavorite }
                )
            }
        ) { padding ->
            when (val state = baseState) {
                is PokedexBaseState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        contentAlignment = Alignment.Center
                    ) { OrbitingSparkProgress() }
                }

                is PokedexBaseState.ShowScreenError -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = stringResource(R.string.pokedex_pokemon_detail_load_error),
                                color = onColor
                            )
                            Spacer(Modifier.height(8.dp))
                            OutlinedButton(
                                onClick = { viewModel.retry() },
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = onColor),
                                border = BorderStroke(1.dp, onColor)
                            ) {
                                Text(text = stringResource(R.string.pokedex_pokemon_detail_retry))
                            }
                        }
                    }
                }

                is PokedexBaseState.Content -> {
                    DetailContent(
                        model = state.data,
                        onColor = onColor,
                        padding = padding,
                        backgroundColor = backgroundColor
                    )
                }

                else -> Unit
            }
        }
    }
}

@Composable
private fun DetailContent(
    model: PokemonDetailModel,
    onColor: Color,
    padding: PaddingValues,
    backgroundColor: Color
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = padding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 0.45f)
                .padding(vertical = 8.dp, horizontal = 12.dp)
        ) {
            val chipHeightPx = getChipsHeightPx()
            val chipHeightDp = getChipsHeightDp(chipHeightPx)

            PokemonImage(
                modifier = Modifier.matchParentSize(),
                model = model,
                chipHeightDp = chipHeightDp,
                backgroundColor = backgroundColor
            )
            PokemonIdText(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 4.dp, top = 4.dp)
                    .widthIn(max = 240.dp),
                pokemonId = model.id,
                onColor = onColor
            )
            PokemonChips(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp)
                    .onGloballyPositioned { layoutCoordinates ->
                        chipHeightPx.intValue = layoutCoordinates.size.height
                    },
                pokemonTypes = model.types,
                onColor = onColor
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.55f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PokemonPills(
                weight = model.weight,
                height = model.height,
                onColor = onColor
            )
            Spacer(Modifier.height(14.dp))
            PokemonBaseStats(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                stats = model.stats,
                onColor = onColor
            )
        }
    }
}

@Composable
private fun getBackgroundColor(baseState: PokedexBaseState<PokemonDetailModel>) =
    when (val state = baseState) {
        is PokedexBaseState.Content -> {
            val dominant = PokemonTypeColor.dominantType(types = state.data.types)
            PokemonTypeColor.backgroundFor(type = dominant)
        }

        else -> MaterialTheme.colorScheme.surface
    }

@Composable
private fun PokemonImage(
    modifier: Modifier,
    model: PokemonDetailModel,
    chipHeightDp: Dp,
    backgroundColor: Color
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        PokemonHalo(pokemonType = model.types)
        BackgroundPokeball(
            modifier = Modifier.matchParentSize(),
            contentAlignment = Alignment.CenterEnd,
            backgroundColor = backgroundColor,
            size = 80.dp
        )
        PokemonAsyncImage(
            imageUrl = model.imageUrl,
            name = model.name,
            paddingBottom = chipHeightDp
        )
    }
}

@Composable
private fun PokemonIdText(modifier: Modifier, pokemonId: Int, onColor: Color) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = UiFormatters.formatPokedexId(pokemonId),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = onColor
        )
    }
}

@Composable
private fun PokemonChips(
    modifier: Modifier,
    pokemonTypes: List<PokemonType>,
    onColor: Color
) {
    Box(modifier = modifier) {
        PokedexTypeChips(
            types = pokemonTypes,
            onChipClick = {},
            borderColor = onColor
        )
    }
}

@Composable
private fun PokemonPills(weight: Int?, height: Int?, onColor: Color) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PokedexInfoPill(
            label = stringResource(R.string.pokedex_pokemon_detail_weight),
            value = UiFormatters.formatWeightToKg(weight),
            onColor = onColor
        )
        PokedexInfoPill(
            label = stringResource(R.string.pokedex_pokemon_detail_height),
            value = UiFormatters.formatHeightToM(height),
            onColor = onColor
        )
    }
}

@Composable
private fun PokemonBaseStats(modifier: Modifier, stats: List<StatModel>, onColor: Color) {
    Text(
        text = stringResource(R.string.pokedex_pokemon_detail_base_stats),
        style = MaterialTheme.typography.titleMedium,
        color = onColor,
        modifier = modifier
    )

    OutlinedCard(
        border = BorderStroke(1.dp, onColor),
        colors = CardDefaults.outlinedCardColors(
            containerColor = PkOnPrimaryWhite.copy(alpha = 0.10f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp)
    ) {
        Column(
            Modifier.padding(vertical = 12.dp, horizontal = 14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            stats.forEach { stat ->
                StatRow(
                    name = stat.name,
                    value = stat.base,
                    onColor = onColor
                )
            }
        }
    }
}

@Composable
private fun PokemonHalo(pokemonType: List<PokemonType>) {
    val dominant = PokemonTypeColor.dominantType(pokemonType)
    val haloColor = PokemonTypeChipColor.vibrantFor(dominant)

    // Black Halo
    Box(
        modifier = Modifier
            .fillMaxHeight(0.95f)
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = 0.40f),
                        Color.Transparent
                    )
                )
            )
            .blur(48.dp)
    )

    // Light color Halo
    Box(
        modifier = Modifier
            .fillMaxHeight(0.80f)
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        haloColor.copy(alpha = 0.40f),
                        Color.Transparent
                    )
                )
            )
            .blur(36.dp)
    )
}

@Composable
private fun getChipsHeightPx() = remember { mutableIntStateOf(0) }

@Composable
private fun getChipsHeightDp(chipHeightPx: MutableIntState): Dp =
    with(LocalDensity.current) { chipHeightPx.intValue.toDp() }

@Composable
private fun PokemonAsyncImage(imageUrl: String?, name: String, paddingBottom: Dp) {
    AsyncImage(
        model = imageUrl,
        contentDescription = name,
        modifier = Modifier
            .fillMaxHeight()
            .padding(bottom = paddingBottom),
        contentScale = ContentScale.Fit
    )
}

@Composable
private fun StatRow(name: String, value: Int, onColor: Color) {
    Column(Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = name.toStatAbbr(),
                style = MaterialTheme.typography.bodyMedium,
                color = onColor
            )
            Text(
                text = value.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = onColor.copy(alpha = 0.9f)
            )
        }
        Spacer(Modifier.height(6.dp))
        LinearProgressIndicator(
            progress = { value.coerceIn(0, 255) / 255f },
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .clip(RoundedCornerShape(50)),
            color = ProgressBarColor,
            trackColor = onColor.copy(alpha = 0.22f)
        )
    }
}

// Abreviaturas amigables (local por ahora; luego, si quieres, lo movemos a domain/common)
private fun String.toStatAbbr(): String = when (lowercase()) {
    "hp" -> "HP"
    "attack" -> "ATK"
    "defense" -> "DEF"
    "special-attack" -> "Sp. ATK"
    "special-defense" -> "Sp. DEF"
    "speed" -> "SPD"
    else -> replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}