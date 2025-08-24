package com.emdp.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.emdp.data.repository.SyncPokedexRepositoryImpl
import com.emdp.data.source.local.database.PokedexDatabase
import com.emdp.data.source.local.datastore.SyncLocalDataSource
import com.emdp.data.source.local.datastore.SyncLocalDataSourceImpl
import com.emdp.data.source.remote.PokemonRemoteDataSource
import com.emdp.data.source.remote.PokemonRemoteDataSourceImpl
import com.emdp.data.source.remote.api.PokeApiService
import com.emdp.data.source.remote.mapper.PokemonRemoteMapper
import com.emdp.data.source.remote.mapper.PokemonRemoteMapperImpl
import com.emdp.domain.repository.SyncPokedexRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.Locale
import java.util.concurrent.TimeUnit

private const val DATASTORE_NAME = "pokedex_prefs"
private const val DEFAULT_POKE_API_BASE_URL = "https://pokeapi.co/api/v2/"

val pokedexDataDiModule = module {
    singleOf(::SyncPokedexRepositoryImpl) { bind<SyncPokedexRepository>() }
    singleOf(::SyncLocalDataSourceImpl) { bind<SyncLocalDataSource>() }

    singleOf(::PokemonRemoteMapperImpl) { bind<PokemonRemoteMapper>() }
    singleOf(::PokemonRemoteDataSourceImpl) { bind<PokemonRemoteDataSource>() }
}

val pokedexDataStoreDiModule = module {
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler { emptyPreferences() },
            produceFile = { get<Context>().preferencesDataStoreFile(DATASTORE_NAME) }
        )
    }
}

val pokedexRoomModule = module {
    single(named("dbName")) { "pokedex.db" }

    // DB
    single {
        val ctx: Context = get()
        val dbName: String = get(named("dbName"))
        val isDebug: Boolean = get(named("isDebug"))
        val wipe: Boolean = get(named("wipeDbOnDebug"))

        if (isDebug && wipe) ctx.deleteDatabase(dbName)

        Room.databaseBuilder(ctx, PokedexDatabase::class.java, dbName)
            .build()
    }

    // DAO
    single { get<PokedexDatabase>().pokemonListDao() }
}

val devTogglesModule = module {
    // Poner a true SOLO cuando se quiera resetear la BBDD en el proximo arranque
    single(named("wipeDbOnDebug")) { false }
}

val pokedexNetworkModule = module {
    single(named("pokeapiBaseUrl")) { DEFAULT_POKE_API_BASE_URL }
    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
    single {
        val isDebug = get<Boolean>(named("isDebug"))
        HttpLoggingInterceptor().apply {
            level = if (isDebug) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
    }
    single(named("commonHeaders")) {
        Interceptor { chain ->
            val ctx = get<Context>()
            val ua = userAgent(ctx)
            val req = chain.request()
                .newBuilder()
                .header("Accept", "application/json")
                .header("Accept-Language", Locale.getDefault().toLanguageTag())
                .header("User-Agent", ua)
                .build()
            chain.proceed(req)
        }
    }
    single {
        val cacheDir = File(get<Context>().cacheDir, "http_cache")
        Cache(cacheDir, 10L * 1024L * 1024L)
    }
    single {
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(get<String>(named("pokeapiBaseUrl")))
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }
    single<PokeApiService> { get<Retrofit>().create(PokeApiService::class.java) }
}

private fun userAgent(context: Context): String {
    val pkPackageManager = context.packageManager
    val pkPackageInfo = pkPackageManager.getPackageInfo(context.packageName, 0)
    val versionName = pkPackageInfo.versionName ?: "0"
    val buildVersion = android.os.Build.VERSION.RELEASE ?: "unknown"
    return "Pokedex/${versionName} (Android ${buildVersion}; ${context.packageName})"
}