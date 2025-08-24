package com.emdp.pokedex

import android.content.Context
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext

val pokedexAppDiModule = module {
    single(named("isDebug")) {
        val ctx: Context = androidContext()
        (ctx.applicationInfo.flags and android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE) != 0
    }
}