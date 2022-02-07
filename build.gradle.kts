import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.2" //LICENSE OK
    id("io.spring.dependency-management") version "1.0.11.RELEASE" //LICENSE OK
    id("com.palantir.git-version") version "0.12.3" //LICENSE OK
    kotlin("jvm") version "1.6.10" //LICENSE OK
    kotlin("plugin.spring") version "1.6.10" //LICENSE OK
}

group = "xyz.miyayu.yobsub"
version = "3.0.2"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

val versionDetails: groovy.lang.Closure<com.palantir.gradle.gitversion.VersionDetails> by extra
val details = versionDetails()
val versions = details.lastTag
val implementationVersion = versions + ',' + (details.gitHash).substring(0,7)


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web") //LICENSE OK
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin") //LICENSE OK
    implementation("org.jetbrains.kotlin:kotlin-reflect") //LICENSE OK
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8") //LICENSE OK
    implementation("io.github.cdimascio:dotenv-kotlin:6.2.2") //LICENSE OK
    implementation("net.dv8tion:JDA:5.0.0-alpha.5") //LICENSE OK
    testImplementation("org.springframework.boot:spring-boot-starter-test") //LICENSE OK
    implementation("org.codehaus.groovy:groovy-all:3.0.9") //LICENSE OK
    implementation("org.xerial:sqlite-jdbc:3.36.0.3") //LICENSE OK
    implementation("org.json:json:20211205") //LICENSE OK


}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"

    }

}
tasks.withType<Jar> {
    manifest {
        attributes["Implementation-Version"] = implementationVersion
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
