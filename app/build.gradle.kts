plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.search.news"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.search.news"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures{
        viewBinding = true
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.work:work-runtime-ktx:2.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    val fragment_version = "1.6.2"
    implementation("androidx.fragment:fragment-ktx:$fragment_version")

    val dagger_version = "2.49"
    implementation("com.google.dagger:dagger:$dagger_version")
    kapt("com.google.dagger:dagger-compiler:$dagger_version")

    val retrofit_version = "2.9.0"

    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:adapter-rxjava3:$retrofit_version")

    val rxJava_version = "3.1.8"
    implementation("io.reactivex.rxjava3:rxjava:$rxJava_version")

    val rxAndroid_version = "3.0.2"
    implementation("io.reactivex.rxjava3:rxandroid:$rxAndroid_version")

    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    kapt("androidx.room:room-compiler:$room_version")


    implementation("androidx.room:room-ktx:$room_version")


    implementation("androidx.room:room-rxjava3:$room_version")

    val moxyVersion = "2.2.2"
    implementation("com.github.moxy-community:moxy:$moxyVersion")
    implementation("com.github.moxy-community:moxy-ktx:$moxyVersion")
    implementation("com.github.moxy-community:moxy-androidx:$moxyVersion")
    kapt("com.github.moxy-community:moxy-compiler:$moxyVersion")

    val coroutines_version = "1.7.3"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")




    val lifeCycleVersion ="2.6.2"
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifeCycleVersion")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifeCycleVersion")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifeCycleVersion")

    val glideVersion = "4.16.0"
    implementation("com.github.bumptech.glide:glide:$glideVersion")

    val lottieVersion = "6.2.0"
    implementation("com.airbnb.android:lottie:$lottieVersion")

    implementation("androidx.core:core-splashscreen:1.0.1")


    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")


    val orbitVersion = "6.1.0"

    implementation("org.orbit-mvi:orbit-core:$orbitVersion")
    implementation("org.orbit-mvi:orbit-viewmodel:$orbitVersion")
}