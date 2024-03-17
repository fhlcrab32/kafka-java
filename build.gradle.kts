plugins {
    id("java")
    id("com.github.davidmc24.gradle.plugin.avro") version "1.9.1"
    kotlin("jvm")
}

private val assertVersion = "3.25.3"
private val avroVersion = "1.11.3"
private val guavaVersion = "33.1.0-jre"
private val jacksonVersion = "2.17.0"
private val junitVersion = "5.9.1"
private val kafkaVersion = "3.7.0"
private val kafkaAvroVersion = "7.6.0"
private val slf4jVersion ="2.0.12"

group = "com.learn"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.guava:guava:$guavaVersion")
    implementation("com.google.guava:failureaccess:1.0.2")
    implementation("com.fasterxml.jackson.core:jackson-core:$jacksonVersion")
    implementation("org.apache.kafka:kafka-clients:$kafkaVersion")
    implementation("org.apache.kafka:kafka-streams:$kafkaVersion")
    implementation("io.confluent:kafka-avro-serializer:$kafkaAvroVersion")

    implementation("org.apache.avro:avro:$avroVersion")

    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:$assertVersion")
    testImplementation("org.slf4j:slf4j-simple:$slf4jVersion")
    implementation(kotlin("stdlib-jdk8"))


}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}