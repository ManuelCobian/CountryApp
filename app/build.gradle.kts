plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id ("dagger.hilt.android.plugin")
    id ("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.luvsoft.countryapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.luvsoft.countryapp"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures{
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    //qamarelsafadi NAV
    implementation(libs.nav.curves)

    //Dagger Hitl
    implementation(libs.androidx.hitl.kotlin)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    kapt(libs.androidx.hitl.kpt)

    //LIVE DATA
    implementation(libs.androidx.live.data)

    //NAVIGATION
    implementation(libs.androidx.navigate.ui)
    implementation(libs.androidx.navigate.fragment)

    //GLIDE
    implementation(libs.androidx.glide)

    //CARDVIEW
    implementation(libs.androidx.card.view)

    //VIEW MODEL
    implementation(libs.androidx.view.model)

    //RETRFIT
    implementation(libs.androidx.retrofit)

    //GSON CONVERT
    implementation(libs.androidx.retrofit.gson)
    implementation(libs.androidx.retrofit.gson.conver)

    //ROOM
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)

    //LIFECYCLE
    implementation(libs.android.lifecycle.runtime)

    //CORRUTIENES
    implementation(libs.android.corrutienes)

    implementation(libs.android.refresh)


    implementation(project(":base"))

    implementation(project(":rooom"))

    implementation(project(":core"))

    testImplementation(libs.junit)
    testImplementation(libs.junit)
    testImplementation("junit:junit:4.13.2")
    testImplementation ("org.mockito:mockito-core:5.12.0")
    testImplementation ("org.mockito.kotlin:mockito-kotlin:5.2.1")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
    testImplementation(kotlin("test"))
}
kapt {
    correctErrorTypes = true
    arguments {
        arg("--add-exports", "jdk.compiler/com.sun.tools.javac.main=ALL-UNNAMED")
    }
}