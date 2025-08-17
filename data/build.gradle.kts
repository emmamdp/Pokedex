plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.emdp.data"
    compileSdk = 36
    defaultConfig { minSdk = 24 }
    kotlinOptions { jvmTarget = "17" }
}

dependencies {
    implementation(projects.domain)
    implementation(projects.core.common)

    // Retrofit / OkHttp / Moshi
    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)
    ksp(libs.room.compiler)

    // Paging + Coroutines + DI
    implementation(libs.paging.runtime)
    implementation(libs.coroutines.core)
    implementation(libs.koin.core)

    // Tests de red/DB
    testImplementation(libs.junit)
    testImplementation(libs.mockwebserver)
}