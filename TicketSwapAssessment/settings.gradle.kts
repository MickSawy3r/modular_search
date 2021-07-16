rootDir
    .walk()
    .maxDepth(1)
    .filter {
        it.name != "buildSrc" && it.isDirectory &&
                (file("${it.absolutePath}/build.gradle.kts").exists())
    }
    .forEach {
        include(":${it.name}")
    }

rootDir
    .walk()
    .maxDepth(1)
    .filter {
        it.name != "buildSrc" && it.isDirectory &&
                file("${it.absolutePath}/build.gradle").exists()
    }
    .forEach {
        include(":${it.name}")
    }

include(
    ":common:cache",
    ":common:network",
    ":common:reactive"
)
