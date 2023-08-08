// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath (Dependencies.BuildPlugins.android)
        classpath (Dependencies.BuildPlugins.kotlin)
    }
}

plugins {
    id ("com.android.application") version "7.4.0" apply false
    id ("com.android.library") version "7.4.0" apply false
    id ("org.jetbrains.kotlin.android") version "1.7.20" apply false
    id ("com.google.dagger.hilt.android") apply false
    id ("androidx.navigation.safeargs") version "2.5.3" apply false
}

tasks.register("clean", Delete::class.java){
    delete(rootProject.buildDir)
}
