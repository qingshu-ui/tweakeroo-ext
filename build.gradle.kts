@file:Suppress("PropertyName")

import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val orbit_version: String by project
val minecraft_version: String by project
val yarn_mappings: String by project
val loader_version: String by project
val kotlin_loader_version: String by project
val malilib_version: String by project

plugins {
    kotlin("jvm") version "2.0.21"
    id("fabric-loom") version "1.7.1"
    id("maven-publish")
}

version = project.property("mod_version") as String
group = project.property("maven_group") as String

base {
    archivesName.set(project.property("archives_base_name") as String)
}

val targetJavaVersion = 21
java {
    toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
    withSourcesJar()
}

repositories {
    maven("https://jitpack.io")
    maven("https://masa.dy.fi/maven")
}

val library: Configuration by configurations.creating
configurations {
    implementation {
        extendsFrom(library)
    }
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraft_version")
    mappings("net.fabricmc:yarn:$yarn_mappings:v2")
    modImplementation("net.fabricmc:fabric-loader:$loader_version")
    modImplementation("net.fabricmc:fabric-language-kotlin:$kotlin_loader_version")

    // tweakeroo
    modCompileOnly("fi.dy.masa.malilib:malilib-fabric-$minecraft_version:$malilib_version")
    modCompileOnly(files("libs/tweakeroo-fabric-1.21-0.21.55.jar"))
    // EssentialGUI
    modCompileOnly(files("libs/EssentialGUI-1.10.1+1.21.jar"))

    // library
    library("com.github.qingshu-ui:orbit:$orbit_version")
}

tasks {
    processResources {
        val properties = mapOf(
            "version" to project.version,
            "minecraft_version" to minecraft_version,
            "loader_version" to loader_version,
            "kotlin_loader_version" to kotlin_loader_version,
        )
        inputs.properties(properties)
        filteringCharset = "UTF-8"

        filesMatching("fabric.mod.json") {
            expand(properties)
        }
    }

    jar {
        from("LICENSE") {
            rename { "${it}_${project.base.archivesName}" }
        }
        from(library.map { if (it.isDirectory) it else zipTree(it) })
    }

    withType<KotlinCompile>().configureEach {
        compilerOptions.jvmTarget.set(JvmTarget.fromTarget(targetJavaVersion.toString()))
    }

    withType<JavaCompile>()
        .configureEach {
            options.encoding = "UTF-8"
            options.release.set(targetJavaVersion)
        }

    withType<Jar> {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = project.property("archives_base_name") as String
            from(components["java"])
        }
    }

    repositories {
    }
}
