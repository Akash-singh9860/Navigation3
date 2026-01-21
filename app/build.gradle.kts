plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinCompose)
}

android {
    namespace = "com.app.navigation3"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.app.navigation3"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)

    // Lifecycle (ViewModel + StateFlow collection)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Navigation (you can keep both or remove traditional navigation-compose later)
    implementation(libs.androidx.navigation.compose)

    // Navigation 3
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)

    // Hilt (DI)
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    // Network
    implementation(libs.retrofit)
    implementation(libs.retrofit.serialization.converter)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
}
