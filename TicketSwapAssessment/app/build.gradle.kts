plugins {
    // Application Specific Plugins
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.androidHilt)
    id(BuildPlugins.kotlinParcelize)

    // Internal Script plugins
    id(ScriptPlugins.variants)
    id(ScriptPlugins.quality)
    id(ScriptPlugins.compilation)
}

android {
    compileSdkVersion(AndroidSdk.compile)

    defaultConfig {
        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)

        applicationId = AndroidClient.appId
        versionCode = AndroidClient.versionCode
        versionName = AndroidClient.versionName
        testInstrumentationRunner = AndroidClient.testRunner

        buildConfigField("String", "SPOTIFY_BASE_URL", "\"https://api.spotify.com/v1/\"")

        javaCompileOptions {
            annotationProcessorOptions {
                argument("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }

    sourceSets {
        map { it.java.srcDir("src/${it.name}/java") }
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Modules and Projects
    implementation(project(":common:cache"))
    implementation(project(":common:reactive"))
    implementation(project(":common:network"))
    implementation(project(":common:extension"))
    implementation(project(":common:platform"))
    implementation(project(":common:extension"))
    implementation(project(":authenticator"))

    annotationProcessor(Libraries.roomAnnotationProcessor)

    // Compile time dependencies
    kapt(Libraries.lifecycleCompiler)
    kapt(Libraries.hiltCompiler)
    kapt(Libraries.roomCompiler)

    // Application dependencies
    implementation(Libraries.roomRuntime)
    implementation(Libraries.roomRxAdapter)
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.kotlinCoroutines)
    implementation(Libraries.kotlinCoroutinesAndroid)
    implementation(Libraries.swipeRefreshLayout)
    implementation(Libraries.appCompat)
    implementation(Libraries.ktxCore)
    implementation(Libraries.constraintLayout)
    implementation(Libraries.viewModel)
    implementation(Libraries.liveData)
    implementation(Libraries.lifecycleExtensions)
    implementation(Libraries.cardView)
    implementation(Libraries.recyclerView)
    implementation(Libraries.material)
    implementation(Libraries.androidAnnotations)
    implementation(Libraries.picasso)
    implementation(Libraries.hilt)
    implementation(Libraries.spotify)
    implementation(Libraries.moshiAdapter)
    implementation(Libraries.okHttp)
    implementation(Libraries.okHttpLoggingInterceptor)
    implementation(Libraries.rxAndroid)
    implementation(Libraries.rxJava)
    implementation(Libraries.lottie)

    // Ktx
    implementation(Libraries.fragmentKtx)

    // Unit/Android tests dependencies
    testImplementation(TestLibraries.junit4)
    testImplementation(TestLibraries.kluent)
    testImplementation(TestLibraries.mockitoCore)
    testImplementation(TestLibraries.mockitoInline)
    testImplementation(TestLibraries.mockitoJUnitJupyter)
    testImplementation(TestLibraries.robolectric)

    // Test Runtime
    testRuntimeOnly(TestRuntimeLibraries.jUnitJupyter)

    // Acceptance tests dependencies
    androidTestImplementation(TestLibraries.testRunner)
    androidTestImplementation(TestLibraries.espressoCore)
    androidTestImplementation(TestLibraries.testExtJunit)
    androidTestImplementation(TestLibraries.testRules)
    androidTestImplementation(TestLibraries.espressoIntents)
    androidTestImplementation(TestLibraries.hiltTesting)

    // Development dependencies
    debugImplementation(DevLibraries.leakCanary)
}
