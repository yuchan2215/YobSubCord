import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.2" //LICENSE OK
    id("io.spring.dependency-management") version "1.0.11.RELEASE" //LICENSE OK
    kotlin("jvm") version "1.6.10" //LICENSE OK
    kotlin("plugin.spring") version "1.6.10" //LICENSE OK
}

group = "xyz.miyayu.yobsub"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web") //LICENSE OK
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin") //LICENSE OK
    implementation("org.jetbrains.kotlin:kotlin-reflect") //LICENSE OK
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8") //LICENSE OK
    implementation("io.github.cdimascio:dotenv-kotlin:6.2.2") //LICENSE OK
    testImplementation("org.springframework.boot:spring-boot-starter-test") //LICENSE OK
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
