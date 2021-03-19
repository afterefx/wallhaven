// Top-level build file where you can add configuration options common to all sub-projects/modules.
val composeVersion: String by extra { "1.0.0-beta02" }
val kotlinVersion: String by extra { "1.4.31" }
val hiltVersion: String by extra { "2.33-beta" }
buildscript {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.0-alpha09")
        classpath(kotlin("gradle-plugin", version = "1.4.31"))
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.33-beta")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }

}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
}

tasks.create<Delete>("clean") {
    delete(rootProject.buildDir)
}
