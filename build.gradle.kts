import io.franzbecker.gradle.lombok.task.DelombokTask
import org.checkerframework.gradle.plugin.CheckerFrameworkExtension

plugins {
    id("java")
    id("org.checkerframework") version "0.6.13"
    id("io.franzbecker.gradle-lombok") version "5.0.0"
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
    testImplementation("io.kotest:kotest-runner-junit5:5.3.1")
}

lombok {
    version = "1.18.22"
    sha256 = ""
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    withJavadocJar()
}

configure<CheckerFrameworkExtension> {

}

val delombok by tasks.registering(DelombokTask::class) {
    mainClass.set(lombok.main)
    dependsOn(tasks.compileJava)
    val outputDir by extra { layout.buildDirectory.dir("delombok").get() }
    outputs.dir(outputDir)
    sourceSets["main"].java.srcDirs.forEach {
        inputs.dir(it)
        args(it, "-d", outputDir)
    }
    doFirst {
        delete(outputDir)
    }
}

tasks.javadoc {
    dependsOn(delombok)
    val outputDir: Directory by delombok.get().extra
    source = outputDir.asFileTree
}

val sourcesJar by tasks.creating(Jar::class) {
    dependsOn(delombok)
    from(delombok)
    archiveClassifier.set("sources")
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
            artifact(sourcesJar) {
                classifier = "sources"
            }
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications["maven"])
}
