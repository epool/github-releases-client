import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.30"
    maven
}

group = "com.github.epool"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
    maven("https://dl.bintray.com/spekframework/spek")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.squareup.retrofit2", "retrofit", "2.5.0")
    implementation("com.squareup.retrofit2", "converter-gson", "2.5.0")
    implementation("com.google.code.gson", "gson", "2.8.5")
    implementation("com.squareup.okhttp3", "logging-interceptor", "3.14.1")

    val spekVersion = "2.0.2"
    testImplementation(kotlin("test"))
    testImplementation("org.spekframework.spek2", "spek-dsl-jvm", spekVersion)
    testRuntimeOnly("org.spekframework.spek2", "spek-runner-junit5", spekVersion)
    testRuntimeOnly(kotlin("reflect"))
}

tasks.withType<Test> {
    useJUnitPlatform {
        includeEngines("spek2")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}