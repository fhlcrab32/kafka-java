rootProject.name = "kafka-java"



pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
        kotlin("jvm") version "1.9.23"
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

buildscript {
    dependencies {
        classpath("org.apache.avro:avro-tools:1.11.3")
    }
}