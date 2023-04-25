plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("javax.xml.stream:stax-api:1.0-2")
}

tasks.test {
    useJUnitPlatform()
}