package com.emdp.pokedex

import android.app.Application
import com.emdp.core.di.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PokedexApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(androidContext = this@PokedexApp)
            modules(modules = allModules())
        }
    }
}