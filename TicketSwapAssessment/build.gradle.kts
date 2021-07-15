plugins {
    id(ScriptPlugins.infrastructure)
}

buildscript {
    val kotlinVersion by extra("1.5.10")

    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://oss.jfrog.org/libs-snapshot")
        }
    }

    dependencies {
        classpath(BuildPlugins.androidGradlePlugin)
        classpath(BuildPlugins.kotlinGradlePlugin)
        classpath(BuildPlugins.hiltGradlePlugin)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://oss.jfrog.org/libs-snapshot")
        }
    }
}
