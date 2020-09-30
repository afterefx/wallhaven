plugins {
    id("com.android.application") version "4.2.0-alpha12" apply false
    kotlin("android") version "1.4.10" apply false
    id("hilt.agp") version "2.28.3-alpha" apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
}

