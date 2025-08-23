package com.emdp.core.common.base

import androidx.lifecycle.ViewModel
import com.emdp.core.common.base.PokedexBaseState.Content
import com.emdp.core.common.base.PokedexBaseState.Error
import com.emdp.core.common.base.PokedexBaseState.Loading
import com.emdp.core.common.base.PokedexBaseState.NavigateToNextView
import com.emdp.core.navigation.PokedexDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class PokedexBaseViewModel<S> : ViewModel() {

    protected open fun initialScreenState(): PokedexBaseState<S> = Loading

    private val _screenState = MutableStateFlow(initialScreenState())
    val screenState: StateFlow<PokedexBaseState<S>> = _screenState.asStateFlow()

    private var lastStableState: PokedexBaseState<S> = initialScreenState()

    protected fun setLoading() {
        _screenState.value = Loading
        lastStableState = Loading
    }

    protected fun setError(message: String? = null) {
        val errorState = Error(message)
        _screenState.value = errorState
        lastStableState = errorState
    }

    protected fun setContent(data: S) {
        val contentState = Content(data)
        _screenState.value = contentState
        lastStableState = contentState
    }

    protected fun updateContent(newState: (S) -> S) {
        val currentState = _screenState.value
        if (currentState is Content) {
            val contentState = Content(newState(currentState.data))
            _screenState.value = contentState
            lastStableState = contentState
        }
    }

    protected fun navigateTo(destination: PokedexDestination) {
        _screenState.value = NavigateToNextView(destination)
    }

    fun restoreAfterNavigation() {
        _screenState.value = lastStableState
    }
}