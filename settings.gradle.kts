pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "VocalInk"
include(":app")
include(":core:voice")
include(":core:ui")
include(":core:utils")
include(":features:voiceToText")
include(":features:voiceHistory")
include(":data:voice")
include(":data:timer")
include(":domain:voice")
include(":domain:timer")

