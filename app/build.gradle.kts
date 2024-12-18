plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.myimagegallery"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myimagegallery"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    implementation("io.github.chrisbanes:photoview:2.3.0")
    implementation ("androidx.viewpager2:viewpager2:1.0.0")

    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")
}