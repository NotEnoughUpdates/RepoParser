import org.checkerframework.gradle.plugin.CheckerFrameworkExtension

plugins {
    id("java")
    id("org.checkerframework") version "0.6.13"
    `maven-publish`
    signing
}

group = "io.github.moulberry"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.9.0")
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
    testImplementation("io.kotest:kotest-runner-junit5:5.3.1")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

configure<CheckerFrameworkExtension> {

}

tasks.withType<JavaCompile>() {
    sourceCompatibility = "1.8"
    targetCompatibility = "1.8"
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
tasks.processResources {
    filesMatching("neurepoparser.properties") {
        expand(
            "VERSION" to version
        )
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications["maven"])
}
