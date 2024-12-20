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

rootProject.name = "ComposeTest"
include(":app")
include(":challenge2")
include(":challenge3")
include(":challenge4")
include(":challenge5")
include(":challenge6")
include(":challenge6")
include(":challenge_6")
include(":challenge6_")
include(":advancedstateandsideeffectscodelab")
include(":challenge6-")
