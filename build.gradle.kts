// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:8.6.1")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.21")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.52")
        classpath ("org.jetbrains.kotlin:compose-compiler-gradle-plugin:2.0.21")
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
}