plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

repositories {
    google()
    mavenCentral()
    maven("https://maplibre.org/maven/")
}

android {
    namespace = "com.odevs.thassosscooterandatwfinder"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.odevs.thassosscooterandatwfinder"
        minSdk = 24
        targetSdk = 35
        versionCode = 8
        versionName = "1.7"
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

    composeOptions {
        kotlinCompilerExtensionVersion = "1.6.1"
    }
}

dependencies {
    // Jetpack Compose + alap
    implementation(platform("androidx.compose:compose-bom:2024.04.01"))
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-compose")
    implementation("androidx.activity:activity-compose")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.compose.material:material-icons-extended:1.6.5")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // Adatkezelés
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Képek, animációk, hálózat
    implementation("io.coil-kt:coil-compose:2.5.0")
    implementation("com.airbnb.android:lottie-compose:6.1.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // CSV
    implementation("com.opencsv:opencsv:5.8")

    // MapLibre – de kizárjuk a régi support libet!
    implementation("org.maplibre.gl:android-sdk:9.5.0") {
        exclude(group = "com.android.support")
    }

    // Tesztelés
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.0")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
