subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        version.set("1.8.0")
    }
    repositories {
        mavenCentral()
    }
}
tasks.register<Exec>("composeDown") {
    description = "Stops and removes the containers created by compose up"
    commandLine("docker", "compose", "down")
}

extra["composeFileDir"] = layout.projectDirectory