plugins {
    kotlin("jvm") version "2.2.21"
}

group = "com.github.v-tempe"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("net.jqwik:jqwik-kotlin:1.9.3")
}

tasks.test {
    useJUnitPlatform()
}
