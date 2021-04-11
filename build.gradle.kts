// Top-level build file where you can add configuration options common to all sub-projects/modules.
val composeVersion: String by extra { "1.0.5" }
val kotlinVersion: String by extra { "1.5.31" }
val hiltVersion: String by extra { "2.40.5" }
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath(kotlin("gradle-plugin", version = "1.5.31"))
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.40.5")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }

}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.create<Delete>("clean") {
    delete(rootProject.buildDir)
}
