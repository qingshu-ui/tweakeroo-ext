@file:Suppress("PropertyName")

import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val orbit_version: String by project
val minecraft_version: String by project
val yarn_mappings: String by project
val loader_version: String by project
val kotlin_loader_version: String by project
val reflections_version: String by project

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
}

dependencies { // To change the versions see the gradle.properties file
    minecraft("com.mojang:minecraft:$minecraft_version")
    mappings("net.fabricmc:yarn:$yarn_mappings:v2")
    modImplementation("net.fabricmc:fabric-loader:$loader_version")
    modImplementation("net.fabricmc:fabric-language-kotlin:$kotlin_loader_version")

    // tweakeroo
    modCompileOnly(files("libs/malilib-fabric-1.21.1-0.21.0.jar"))
    modCompileOnly(files("libs/tweakeroo-fabric-1.21.1-0.21.50.jar"))
    // EssentialGUI
    modCompileOnly(files("libs/EssentialGUI-1.10.1+1.21.jar"))

    // library
    implementation("com.github.qingshu-ui:orbit:$orbit_version")
    implementation("org.reflections:reflections:$reflections_version")
}

tasks.processResources {
    inputs.property("version", project.version)
    inputs.property("minecraft_version", minecraft_version)
    inputs.property("loader_version", loader_version)
    filteringCharset = "UTF-8"

    filesMatching("fabric.mod.json") {
        expand(
            "version" to project.version,
            "minecraft_version" to minecraft_version,
            "loader_version" to loader_version,
            "kotlin_loader_version" to kotlin_loader_version
        )
    }
}

tasks.withType<JavaCompile>()
    .configureEach {
        options.encoding = "UTF-8"
        options.release.set(targetJavaVersion)
    }

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions.jvmTarget.set(JvmTarget.fromTarget(targetJavaVersion.toString()))
}

tasks.jar {
    from("LICENSE") {
        rename { "${it}_${project.base.archivesName}" }
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
