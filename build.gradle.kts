plugins {
    kotlin("jvm") version "2.0.21"
    kotlin("kapt") version "2.0.21"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("eclipse")
    id("maven-publish")
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.8"
}

group = "kr.apo2073"
version = "1.0"

val relocate = (findProperty("relocate") as? String)?.toBoolean() ?: true

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }
    maven("https://jitpack.io") {
        name = "jitpack"
    }
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "com.github.johnrengelman.shadow")
    apply(plugin = "java")
    apply(plugin = "maven-publish")
    
    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/") {
            name = "papermc-repo"
        }
        maven("https://oss.sonatype.org/content/groups/public/") {
            name = "sonatype"
        }
        maven("https://jitpack.io") {
            name = "jitpack"
        }
        maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    }

    dependencies {
        compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("com.github.apo2073:ApoLib:1.0.4")
        compileOnly("me.clip:placeholderapi:2.11.6")
    }

    val targetJavaVersion = 17
    kotlin {
        jvmToolchain(targetJavaVersion)
    }

    tasks.build {
        dependsOn("shadowJar")
    }
    
    tasks.shadowJar {
        if (relocate) {
            relocate("kr.apo2073.lib", "${rootProject.group}.${rootProject.name}.lib")
        }
    }

    tasks.processResources {
        val props = mapOf("version" to version)
        inputs.properties(props)
        filteringCharset = "UTF-8"
        filesMatching("**/*.yml") {
            expand(props)
        }
    }
}