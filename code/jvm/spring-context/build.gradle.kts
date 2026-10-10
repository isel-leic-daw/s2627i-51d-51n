plugins {
    kotlin("jvm") version "2.2.0"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
}

group = "pt.isel.daw"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework:spring-context:7.0.9")

    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("org.slf4j:slf4j-simple:2.0.16")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

tasks.register("demoTask") {
    group = "Demo"
    description = "Just a demo task to illustrate task definition and execution"
    println("demoTask definition")
    doLast {
        println("demoTask execution")
    }
}

tasks.register("anotherDemoTask") {
    group = "Demo"
    description = "Just another demo task to illustrate task definition and execution"
    dependsOn("demoTask")
    println("anotherDemoTask definition")
    doLast {
        println("anotherDemoTask execution")
    }
}