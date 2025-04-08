plugins {
    kotlin("kapt")
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.fetchhomeassignment"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.fetchhomeassignment"
        minSdk = 26
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    testOptions {
        unitTests.isReturnDefaultValues = false
    }
}

dependencies {
    "kapt"(Deps.hiltAndroidCompiler)
    "kapt"(Deps.hiltCompiler)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Test
    androidTestImplementation(libs.mockk.android)
    testImplementation(libs.mockk)
    testImplementation(Deps.testCoroutines)
    testImplementation(Deps.turbine)

    //DB
    implementation(Deps.runTime)
    "kapt"(Deps.compiler)
    implementation(Deps.roomKtx)
    implementation(Deps.rxJava2)
    implementation(Deps.rxJava3)
    implementation(Deps.guava)
    implementation(Deps.paging)
    testImplementation(Deps.testing)

    // DI
    implementation(Deps.hiltAndroid)
    implementation(Deps.hiltNavigationCompose)

    // HTTP
    implementation(Deps.gson)
    implementation(Deps.retrofit)
    implementation(Deps.gsonRetrofitConverter)
}

object Deps {

    object Versions {
        const val daggerHilt = "2.49"
        const val gson = "2.6.2"
        const val retrofit = "2.1.0"
        const val roomVersion = "2.6.1"
        const val test = "1.8.1"
        const val turbine = "1.0.0"
    }

    // Retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val gsonRetrofitConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

    // Room
    const val runTime = "androidx.room:room-runtime:${Versions.roomVersion}"
    const val compiler = "androidx.room:room-compiler:${Versions.roomVersion}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.roomVersion}"
    const val rxJava2 = "androidx.room:room-rxjava2:${Versions.roomVersion}"
    const val rxJava3 = "androidx.room:room-rxjava3:${Versions.roomVersion}"
    const val guava = "androidx.room:room-guava:${Versions.roomVersion}"
    const val testing = "androidx.room:room-testing:${Versions.roomVersion}"
    const val paging = "androidx.room:room-paging:${Versions.roomVersion}"

    // Dagger-Hilt
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.daggerHilt}"
    const val hiltAndroidCompiler = "com.google.dagger:hilt-compiler:${Versions.daggerHilt}"
    const val hiltCompiler = "androidx.hilt:hilt-compiler:1.0.0"
    const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:1.0.0"

    //Test
    const val testCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.test}"
    const val turbine = "app.cash.turbine:turbine:${Versions.turbine}"
}