import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    id("com.google.gms.google-services")
    alias(libs.plugins.google.firebase.crashlytics)

}

kotlin {

    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class) compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(), iosArm64(), iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            linkerOpts.add("-lsqlite3")
        }
    }

    sourceSets {
         iosMain.dependencies {
                implementation(libs.ktor.client.ios)
            }


        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material) // JetBrains Compose Material
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.multiplatform.settings)
                implementation(libs.kotlinx.datetime)
                implementation(libs.ktor.client.core)  // Core client features
                implementation(libs.ktor.client.cio)   // CIO engine for JVM
                implementation(libs.ktor.client.json)  // JSON support
                implementation(libs.ktor.clint.auth)  // JSON support
                implementation(libs.ktor.client.logging) // Logging
                implementation(libs.ktor.serialization.kotlinx.json) // JSON serialization
                implementation(libs.ktor.client.content.negotiation) // Content Negotiation Plugin

                implementation(libs.androidx.room.runtime)
                implementation(libs.navigation.compose)
                implementation(libs.sqlite.bundled)

                // For shared viewmodels, can be used with iOS too
                implementation(libs.androidx.lifecycle.viewmodel)
                implementation(libs.androidx.lifecycle.runtime.compose)

//                implementation(libs.androidx.lifecycle.livedata.ktx)

                // For shared viewmodels and other common libraries
                api(libs.datastore.preferences)
                api(libs.datastore)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(compose.preview)
                implementation(libs.google.firebase.crashlytics)
                implementation(libs.google.firebase.analytics)
                implementation(libs.lottie.compose)

                implementation(libs.androidx.activity.compose.v193)
                implementation(libs.androidx.room.runtime)
                implementation(libs.sqlite.bundled)
                implementation(libs.ktor.client.okhttp) // OkHttp for Android
                implementation(libs.firebase.messaging) // Firebase for Android

                // AdMob
                implementation(libs.play.services.ads)
            }
        }


    }
}

android {
    namespace = "com.coretechies.jaap"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.coretechies.jaap"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 9
        versionName = "1.9"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    implementation(libs.androidx.navigation.compose)
    implementation(libs.filament.android)
    implementation(libs.androidx.navigation.common.ktx)
    implementation(libs.androidx.datastore.core.android)
    implementation(libs.androidx.datastore.preferences.core.jvm)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.google.firebase.messaging)
    debugImplementation(compose.uiTooling)
    ksp(libs.androidx.room.compiler)
    implementation(libs.firebase.common.ktx)
    //

}