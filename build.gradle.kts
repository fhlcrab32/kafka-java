import org.apache.avro.tool.SpecificCompilerTool

plugins {
    id("java")
    kotlin("jvm")
    application
}

private val assertVersion = "3.25.3"
private val avroVersion = "1.11.3"
private val guavaVersion = "33.1.0-jre"
private val jacksonVersion = "2.17.0"
private val junitVersion = "5.9.1"
private val kafkaVersion = "3.7.0"
private val kafkaAvroVersion = "7.6.0"
private val logbackVersion ="1.5.3"

group = "com.learn"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://packages.confluent.io/maven/")
}

dependencies {
    implementation("com.google.guava:guava:$guavaVersion")
    implementation("com.google.guava:failureaccess:1.0.2")
    implementation("com.fasterxml.jackson.core:jackson-core:$jacksonVersion")
    implementation("org.apache.kafka:kafka-clients:$kafkaVersion")
    implementation("org.apache.kafka:kafka-streams:$kafkaVersion")
    implementation("io.confluent:kafka-avro-serializer:$kafkaAvroVersion")
    implementation("org.apache.avro:avro:$avroVersion")
//    implementation("org.apache.avro:avro-tools:$avroVersion")

    implementation("ch.qos.logback:logback-classic:$logbackVersion")


    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:$assertVersion")
}

sourceSets {
    main {
        java {
            srcDirs("build/avro-generated")
        }
    }
}

val avroCodeGen = "avroCodeGen"

tasks.create(avroCodeGen) {
    doLast {
        SpecificCompilerTool().run(
            System.`in`,
            System.out,
            System.err,
            listOf(
                "-encoding", "UTF-8",
                "-string",
                "-fieldVisibility", "private",
                "-noSetters",
                "-bigDecimal",
                "schema",
                "$projectDir/src/main/avro",
                "$projectDir/src/main/java"
            )
        )
    }
}


tasks.withType<JavaCompile>() {
    dependsOn(avroCodeGen)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}