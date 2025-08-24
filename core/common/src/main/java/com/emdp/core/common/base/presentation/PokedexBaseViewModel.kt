package com.emdp.core.common.base.presentation

import androidx.lifecycle.ViewModel
import com.emdp.core.navigation.PokedexDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class PokedexBaseViewModel<S> : ViewModel() {

    protected open fun initialScreenState(): PokedexBaseState<S> = PokedexBaseState.Loading

    private val _screenState = MutableStateFlow(initialScreenState())
    val screenState: StateFlow<PokedexBaseState<S>> = _screenState.asStateFlow()

    private var lastStableState: PokedexBaseState<S> = initialScreenState()

    protected fun setLoading() {
        _screenState.value = PokedexBaseState.Loading
        lastStableState = PokedexBaseState.Loading
    }

    protected fun setError(message: String? = null) {
        val showScreenErrorState = PokedexBaseState.ShowScreenError(message)
        _screenState.value = showScreenErrorState
        lastStableState = showScreenErrorState
    }

    protected fun setContent(data: S) {
        val contentState = PokedexBaseState.Content(data)
        _screenState.value = contentState
        lastStableState = contentState
    }

    protected fun updateContent(newState: (S) -> S) {
        val currentState = _screenState.value
        if (currentState is PokedexBaseState.Content) {
            val contentState = PokedexBaseState.Content(newState(currentState.data))
            _screenState.value = contentState
            lastStableState = contentState
        }
    }

    protected fun navigateTo(destination: PokedexDestination) {
        _screenState.value = PokedexBaseState.NavigateToNextView(destination)
    }

    fun restoreAfterNavigation() {
        _screenState.value = lastStableState
    }
}