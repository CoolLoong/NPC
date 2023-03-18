plugins {
    alias(libs.plugins.shadowJar)
    java
}

group = "com.github.coolloong"
version = "1.0.0"
var displayName = "NPC"
var isSNAPSHOT = true
var releaseVersion = version.toString() + if (isSNAPSHOT) "-SNAPSHOT" else ""
dependencies {
    compileOnly(libs.minestom)
    compileOnly(libs.lambok)
    annotationProcessor(libs.lambok)
}

tasks {
    shadowJar {
        archiveBaseName.set(displayName)
        archiveClassifier.set("")
        archiveVersion.set("$version-shaded")
    }

    test {
        useJUnitPlatform()
    }

    build {
        dependsOn(shadowJar)
    }

    processResources {
        expand(
            mapOf(
                "name" to displayName,
                "version" to version,
                "releaseVersion" to releaseVersion,
                "mainPackage" to project.group
            ),
        )
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
