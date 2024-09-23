plugins {
    kotlin("jvm") version "2.0.0"
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "io.github.josephsimutis"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.xmlgraphics:batik-all:1.17")
    testImplementation(kotlin("test"))
}

application {
    mainClass = "io.github.josephsimutis.MainKt"
}

javafx {
    version = "17"
    modules("javafx.controls", "javafx.fxml")
}

tasks.test {
    useJUnitPlatform()
}