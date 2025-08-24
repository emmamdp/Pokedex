package com.emdp.pokedex.app

import android.app.Application
import com.emdp.core.di.allModules
import com.emdp.pokedex.pokedexAppDiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PokedexApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(androidContext = this@PokedexApp)
            modules(
                modules = listOf(pokedexAppDiModule) + allModules()
            )
        }
    }
}