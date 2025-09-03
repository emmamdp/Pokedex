package com.emdp.features.pokemon_list.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.emdp.core.ui.R
import com.emdp.core.ui.components.apptopbar.PokedexAppTopBar
import com.emdp.core.ui.components.background.BackgroundPokeball
import com.emdp.core.ui.components.background.pokedexBackgroundGradient
import com.emdp.core.ui.components.card.PokedexCard
import com.emdp.core.ui.components.progressbar.OrbitingSparkProgress
import com.emdp.core.ui.components.searchbar.PokedexSearchBar
import com.emdp.core.ui.theme.PkOnPrimaryWhite
import com.emdp.domain.model.PokemonListModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListRoute(
    viewModel: PokemonListViewModel = koinViewModel(),
    onOpenDetail: (Int) -> Unit = { id -> viewModel.onPokemonClick(id) },
    onBackClick: () -> Unit
) {
    val items: LazyPagingItems<PokemonListModel> = viewModel.pokemonList.collectAsLazyPagingItems()
    val favorites by viewModel.favorites.collectAsStateWithLifecycle()

    Box(
        Modifier
            .fillMaxSize()
            .background(pokedexBackgroundGradient())
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                PokedexAppTopBar(
                    titleText = stringResource(R.string.pokedex_pokemon_list_pokemons),
                    textColor = PkOnPrimaryWhite,
                    onBackClick = onBackClick
                )
            }
        ) { inner ->
            PokemonListScreen(
                items = items,
                onOpenDetail = onOpenDetail,
                contentPadding = inner,
                isFavorite = { id -> favorites.contains(id) },
                onToggleFavorite = { id -> viewModel.onToggleFavorite(id) }
            )
        }
    }
}

@Composable
fun PokemonListScreen(
    items: LazyPagingItems<PokemonListModel>,
    onOpenDetail: (Int) -> Unit,
    contentPadding: PaddingValues,
    isFavorite: (Int) -> Boolean,
    onToggleFavorite: (Int) -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    val isRefreshing = items.loadState.refresh is LoadState.Loading
    var query by rememberSaveable { mutableStateOf("") }

    BackgroundPokeball(modifier = Modifier.fillMaxSize())
    Column(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        PokedexSearchBar(
            query = query,
            onQueryChange = { query = it },
            onSearch = { /* vm.onSearch(it) */ },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        if (isRefreshing && items.itemCount == 0) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(colorScheme.surface),
                contentAlignment = Alignment.Center
            ) { OrbitingSparkProgress() }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    count = items.itemCount,
                    key = { index -> items.peek(index)?.id ?: index }
                ) { index ->
                    val row = items[index] ?: return@items
                    PokedexCard(
                        name = row.name.replaceFirstChar { it.uppercase() },
                        subtitle = "#${row.id}",
                        onItemClick = { onOpenDetail(row.id) },
                        isFavorite = isFavorite(row.id),
                        onToggleFavorite = { onToggleFavorite(row.id) }
                    )
                }

                if (items.loadState.append is LoadState.Loading) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            horizontalArrangement = Arrangement.Center
                        ) { OrbitingSparkProgress() }
                    }
                }
            }
        }
    }
}