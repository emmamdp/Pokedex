package com.emdp.core.common.base.presentation

import com.emdp.core.navigation.PokedexDestination

sealed interface PokedexBaseState<out T> {
    data object Loading : PokedexBaseState<Nothing>

    data class Content<T>(
        val data: T
    ) : PokedexBaseState<T>

    data class ShowScreenError(
        val errorType: String? = null
    ) : PokedexBaseState<Nothing>

    data class NavigateToNextView(
        val destination: PokedexDestination
    ) : PokedexBaseState<Nothing>
}