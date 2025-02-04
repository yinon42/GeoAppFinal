plugins {
    alias(libs.plugins.android.library) // שים לב שזה ספרייה, לא אפליקציה
}

android {
    namespace = "com.example.mylibrary" // השם של הספרייה שלך
    compileSdk = 34

    defaultConfig {
        minSdk = 24 // מינימום גרסת Android
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro") // קובץ פרוגרארד מותאם
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

    buildFeatures {
        viewBinding = true // אפשרות זו שימושית אם תרצה להשתמש ב-View Binding
    }
}

dependencies {



    // Firebase SDK
//    implementation platform("com.google.firebase:firebase-bom:32.0.0")
//    implementation ("com.google.firebase:firebase-firestore")
//
//
//    implementation ("org.locationtech.jts:jts-core:1.18.2")


    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.activity:activity:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Retrofit לתקשורת HTTP
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.google.code.gson:gson:2.8.9")

    // בדיקות
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
