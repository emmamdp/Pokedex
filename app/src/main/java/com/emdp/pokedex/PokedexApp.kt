package com.emdp.pokedex

import android.app.Application
import com.emdp.core.common.di.pokemonCommonDiModule
import com.emdp.data.di.pokemonDataDiModule
import com.emdp.features.pokemon_list.di.pokemonListDiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class PokedexApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidContext(this@PokedexApp)
            modules(
                pokemonCommonDiModule,
                pokemonDataDiModule,
                pokemonListDiModule
            )
        }
    }
}