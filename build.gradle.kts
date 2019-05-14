import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin application project to get you started.
 */

plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin on the JVM.
    id("org.jetbrains.kotlin.jvm").version("1.3.21")

    // Apply the application plugin to add support for building a CLI application.
    application

    java
    id("io.quarkus") version "0.14.0"

}

tasks.withType<JavaCompile> {
    sourceCompatibility = "1.8"
}
tasks.withType<KotlinCompile> {
    sourceCompatibility = "1.8"
    kotlinOptions.jvmTarget = "1.8"
}



repositories {
    // Use jcenter for resolving your dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
    mavenCentral()
}

dependencies {
    implementation("ch.qos.logback:logback-classic:1.2.3")
    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.quarkus:quarkus-resteasy:0.14.0")
    implementation("io.vertx:vertx-web-client:3.7.0")
    implementation("io.vertx:vertx-config:3.7.0")
    implementation("io.vertx:vertx-rx-java2:3.7.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.+")

    // Use the Kotlin test library and JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testCompile("io.quarkus:quarkus-junit5:0.14.0")
    testCompile("io.rest-assured:rest-assured:3.3.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

application {
    // Define the main class for the application.
    mainClassName = "ch.genevajug.AppKt"
}
