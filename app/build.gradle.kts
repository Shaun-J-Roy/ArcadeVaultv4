plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.arcadevaultv4"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.arcadevaultv4"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Core AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // Material Design for fun UI
    implementation(libs.material)

    // Activities, ConstraintLayout
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Navigation Components (for Drawer + Fragments)
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.3")

    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // Preference support (for SettingsActivity)
    implementation("androidx.preference:preference-ktx:1.2.1")

    // Lifecycle (to demonstrate Activity lifecycle methods cleanly)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")

    // FileProvider & APK launching helper
    implementation("androidx.core:core-ktx:1.13.1")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
