plugins {
    id("com.android.application") version "4.2.0-alpha07" apply false
    kotlin("android") version "1.4-M3" apply false
    id("hilt.agp") version "2.28.3-alpha" apply false
}

allprojects {
    repositories {
        maven(url = "https://dl.bintray.com/kotlin/kotlin-eap/")
        jcenter()
        google()
    }
}