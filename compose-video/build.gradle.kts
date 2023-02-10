import org.jetbrains.dokka.gradle.DokkaMultiModuleTask

apply(from = "${rootDir}/publish.gradle")
apply(from = "${rootDir}/scripts/publish-root.gradle")
apply(from = "${rootDir}/scripts/publish-module.gradle")

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("me.tylerbwong.gradle.metalava") version "0.3.2"
    id("org.jetbrains.dokka")
}

metalava {
    filename.set("api/current.api")
    reportLintsAsErrors.set(true)
}

android {
    namespace = "io.sanghun.compose.video"
    compileSdk = 33

    defaultConfig {
        minSdk = 23
        targetSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        compose = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

afterEvaluate {
    tasks.named("dokkaHtmlPartial") {

    }
}

dependencies {
    implementation(libs.androidx.core)
    implementation(libs.androidx.activity)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.exoplayer)
    implementation(libs.material2)

    debugImplementation(libs.bundles.compose.debugOnly)

    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.androidTest)
}
