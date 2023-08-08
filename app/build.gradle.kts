import java.io.File
import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = ConfigData.compileSdkVersion

    val prop = Properties().apply {
        load(FileInputStream(File(rootProject.rootDir, "gradle.properties")))
    }
    val baseAuthUrl = prop.getProperty("BASE_AUTH_URL")
    val baseMainUrl = prop.getProperty("BASE_MAIN_URL")

    defaultConfig {
        applicationId = "com.test.chatterwave.beta"
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName

        buildConfigField("String", "BASE_AUTH_URL", baseAuthUrl)
        buildConfigField("String", "BASE_MAIN_URL", baseMainUrl)
        buildConfigField("String", "DB_NAME", "\"chatterwave_db\"")

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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation(Dependencies.Deps.core)
    implementation(Dependencies.Deps.appCompat)
    implementation(Dependencies.Deps.materialDesign)
    implementation(Dependencies.Deps.constraintLayout)
    implementation(Dependencies.Deps.navigationFragment)
    implementation(Dependencies.Deps.navigationUi)
    testImplementation(Dependencies.Deps.junit)
    androidTestImplementation(Dependencies.Deps.extJunit)
    androidTestImplementation(Dependencies.Deps.espressoCore)

    /* Hilt */
    implementation(Dependencies.Deps.hilt)
    kapt(Dependencies.Deps.hiltCompilerAndroid)
    kapt(Dependencies.Deps.hiltCompiler)

    implementation(Dependencies.Deps.fragment)

    /* Multidex */
    implementation(Dependencies.Deps.multidex)

    /* Lifecycle */
    implementation(Dependencies.Deps.lifecycleRuntime)
    implementation(Dependencies.Deps.lifecycleViewModel)
    implementation(Dependencies.Deps.livedata)
    implementation(Dependencies.Deps.viewModelSaveState)

    /* Room */
    implementation(Dependencies.Deps.room)
    kapt(Dependencies.Deps.roomCompiler)

    /* Retrofit */
    implementation(Dependencies.Deps.retrofit)
    implementation(Dependencies.Deps.retrofitConverterGson)

    /*OkHttp*/
    implementation (Dependencies.Deps.okHttp)
    implementation(Dependencies.Deps.okHttpInterceptor)

    /*SaveArgs*/
    implementation(Dependencies.Deps.saveArgs)
    implementation(Dependencies.Deps.saveArgsUi)
    /* Timber */
    implementation(Dependencies.Deps.timber)

    /*CameraView*/
    implementation(Dependencies.Deps.cameraView)

    /*Glide*/
    implementation(Dependencies.Deps.glide)
    annotationProcessor(Dependencies.Deps.glideCompiler)

    /*Image cropper*/
    implementation(Dependencies.Deps.cropper)

    /*Android Jetpack Paging 3.0*/
    implementation(Dependencies.Deps.paging)

}