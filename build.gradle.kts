// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("org.jetbrains.kotlin.plugin.serialization") version libs.versions.kotlin.get() apply false
}

tasks.register("allTests") {
    group = "verification"
    description = "Runs every unit test and connected Android test in every module."

    // ── 1. All JVM unit-test tasks like :app:testDevDebugUnitTest ──
    dependsOn(
        subprojects.flatMap { project ->
            project.tasks.matching { it.name.startsWith("test") && it.name.endsWith("UnitTest") }
        }
    )

    // ── 2. All connected-device / instrumentation tasks like :app:connectedDevDebugAndroidTest ──
    dependsOn(
        subprojects.flatMap { project ->
            project.tasks.matching { it.name.startsWith("connected") && it.name.endsWith("AndroidTest") }
        }
    )
}
