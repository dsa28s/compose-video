plugins {
    id("com.android.application") version "7.3.0" apply false
    id("com.android.library") version "7.3.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false

    id("com.diffplug.spotless") version "6.14.0"
}

subprojects {
    apply(plugin = "com.diffplug.spotless")

    spotless {
        kotlin {
            target("**/*.kt")
            targetExclude("$buildDir/**/*.kt")

            ktlint("0.48.2")
            licenseHeaderFile(rootProject.file("spotless/copyright.kt"))
        }
    }
}
