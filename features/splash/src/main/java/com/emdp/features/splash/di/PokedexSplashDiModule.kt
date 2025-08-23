package com.emdp.features.splash.di

import com.emdp.features.splash.presentation.PokedexSplashViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val pokedexSplashDiModule = module {
    viewModelOf(::PokedexSplashViewModel)
}