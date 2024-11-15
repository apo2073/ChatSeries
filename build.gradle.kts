plugins {
    kotlin("jvm") version "2.1.0-RC"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("maven-publish")
}

group = "kr.apo2073"
version = "1.0"

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
    }

    dependencies {
        compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("com.github.apo2073:ApoLib:1.0.4")
    }

    val targetJavaVersion = 17
    kotlin {
        jvmToolchain(targetJavaVersion)
    }

    tasks.build {
        dependsOn("shadowJar")
    }

    tasks.processResources {
        val props = mapOf("version" to version)
        inputs.properties(props)
        filteringCharset = "UTF-8"
        filesMatching("plugin.yml") {
            expand(props)
        }
    }
}