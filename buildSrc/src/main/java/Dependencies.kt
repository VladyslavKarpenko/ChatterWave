object Dependencies {

    /**
     * To define plugins
     */
    object BuildPlugins {
        val android by lazy { "com.android.tools.build:gradle:${Versions.gradlePlugin}" }
        val kotlin by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}" }
    }

    /**
     * To define dependencies
     */
    object Deps {
        val core by lazy { "androidx.core:core-ktx:${Versions.core}" }
        val appCompat by lazy { "androidx.appcompat:appcompat:${Versions.appCompat}" }
        val materialDesign by lazy { "com.google.android.material:material:${Versions.material}" }
        val constraintLayout by lazy { "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}" }
        val navigationFragment by lazy { "androidx.navigation:navigation-fragment-ktx:${Versions.navigationFragment}" }
        val navigationUi by lazy { "androidx.navigation:navigation-ui-ktx:${Versions.navigationUi}" }
        val junit by lazy { "junit:junit:${Versions.jUnit}" }
        val extJunit by lazy { "androidx.test.ext:junit:${Versions.extJunit}" }
        val espressoCore by lazy { "androidx.test.espresso:espresso-core:${Versions.espressoCore}" }
        val hilt by lazy { "com.google.dagger:hilt-android:${Versions.hiltVersion}" }
        val hiltCompilerAndroid by lazy { "com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}" }
        val hiltCompiler by lazy { "com.google.dagger:hilt-compiler:${Versions.hiltVersion}" }
        val fragment by lazy { "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}" }
        val multidex by lazy { "androidx.multidex:multidex:${Versions.multidexVersion}" }
        val lifecycleRuntime by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleVersion}" }
        val lifecycleViewModel by lazy { "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleVersion}" }
        val livedata by lazy { "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleVersion}" }
        val viewModelSaveState by lazy { "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycleVersion}" }
        val room by lazy { "androidx.room:room-runtime:${Versions.room}" }
        val roomCompiler by lazy { "androidx.room:room-compiler:${Versions.room}" }
        val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofit}" }
        val retrofitConverterGson by lazy { "com.squareup.retrofit2:converter-gson:${Versions.retrofit}" }
        val saveArgs by lazy { "androidx.navigation:navigation-fragment-ktx:${Versions.saveArgs}" }
        val saveArgsUi by lazy { "androidx.navigation:navigation-ui-ktx:${Versions.saveArgs}" }

        val timber by lazy { "com.jakewharton.timber:timber:${Versions.timber}" }

        val cameraView by lazy { "com.otaliastudios:cameraview:${Versions.cameraView}" }
        val glide by lazy { "com.github.bumptech.glide:glide:${Versions.glide}" }
        val glideCompiler by lazy { "com.github.bumptech.glide:compiler:${Versions.glide}" }
        val cropper by lazy { "com.github.jayrambhia:CropperNoCropper:${Versions.cropper}" }

        val okHttp by lazy { "com.squareup.okhttp3:okhttp:${Versions.okHttp}" }
        val okHttpInterceptor by lazy { "com.squareup.okhttp3:logging-interceptor:4.9.3" }

        val paging by lazy { "androidx.paging:paging-runtime:${Versions.pagingVersion}" }
    }
}