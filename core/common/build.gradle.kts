plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.emdp.core.common"
    compileSdk = 36
    defaultConfig { minSdk = 24 }
    kotlinOptions { jvmTarget = "17" }
}

dependencies {
    implementation(libs.coroutines.core)
    implementation(libs.koin.core)
    implementation(libs.timber)

    testImplementation(libs.junit)
}