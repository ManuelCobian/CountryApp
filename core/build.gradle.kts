plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id ("dagger.hilt.android.plugin")
    id ("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.luvsoft.core"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    //Dagger Hitl
    implementation(libs.androidx.hitl.kotlin)

    kapt(libs.androidx.hitl.kpt)


    //LIVE DATA
    implementation(libs.androidx.live.data)

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
    // ksp(libs.room.compiler)
    implementation(libs.room.ktx)


    //LIFECYCLE
    implementation(libs.android.lifecycle.runtime)

    //CORRUTIENES
    implementation(libs.android.corrutienes)

    implementation(project(":rooom"))

    implementation(project(":base"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

kapt {
    correctErrorTypes = true
    arguments {
        arg("--add-exports", "jdk.compiler/com.sun.tools.javac.main=ALL-UNNAMED")
    }
}