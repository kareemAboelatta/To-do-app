
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    id("kotlin-parcelize")


    id ("kotlin-kapt")
    id("com.google.devtools.ksp")

    id ("dagger.hilt.android.plugin")



}

android {
    namespace = "com.example.noteapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.noteapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.noteapp.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    // Allow references to generated code
    kapt {
        correctErrorTypes = true
    }



}



dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation ("androidx.compose.ui:ui-util:1.5.4")






    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    testImplementation("junit:junit:4.13.2")
    testImplementation ("org.mockito:mockito-core:4.8.0")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")



    //material3
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation("androidx.compose.material3:material3-window-size-class:1.1.2")

    implementation( "androidx.navigation:navigation-compose:2.7.6")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    // Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    //Dagger - Hilt
    implementation ("com.google.dagger:hilt-android:2.50")
    kapt ("com.google.dagger:hilt-android-compiler:2.49")
    kapt ("androidx.hilt:hilt-compiler:1.1.0")

    implementation ("androidx.hilt:hilt-navigation-compose:1.1.0")



    // Local unit tests
    testImplementation( "androidx.test:core:1.5.0")
    testImplementation( "junit:junit:4.13.2")
    testImplementation( "androidx.arch.core:core-testing:2.2.0")
    testImplementation( "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation( "com.google.truth:truth:1.1.5")
    testImplementation( "com.squareup.okhttp3:mockwebserver:4.9.1")
    testImplementation( "io.mockk:mockk:1.10.5")
    debugImplementation( "androidx.compose.ui:ui-test-manifest:1.5.4")

    // Instrumentation tests
    androidTestImplementation( "com.google.dagger:hilt-android-testing:2.50")
    kaptAndroidTest( "com.google.dagger:hilt-android-compiler:2.49")
    androidTestImplementation( "junit:junit:4.13.2")
    androidTestImplementation( "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    androidTestImplementation( "androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation( "com.google.truth:truth:1.1.5")
    androidTestImplementation( "androidx.test.ext:junit:1.1.5")
    androidTestImplementation( "androidx.test:core-ktx:1.5.0")
    androidTestImplementation( "com.squareup.okhttp3:mockwebserver:4.9.1")
    androidTestImplementation( "com.linkedin.dexmaker:dexmaker:2.28.3")
    androidTestImplementation( "io.mockk:mockk-android:1.10.5")
    androidTestImplementation( "androidx.test:runner:1.5.2")


    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")



    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$room_version")
    // To use Kotlin Symbol Processing (KSP)

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")


    // optional - Test helpers
    testImplementation("androidx.room:room-testing:$room_version")


}

