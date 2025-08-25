// Top-level build file where you can add configuration options common to all sub-projects/modules.
// room_version 명시를 통해 build.gradle.kts dependency 에서 활용
buildscript {
    extra.apply {
        set("room_version", "2.6.0")
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
}