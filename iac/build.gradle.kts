plugins {
    java
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("software.amazon.awscdk:aws-cdk-lib:2.149.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.3")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.10.3")
}

application {
    mainClass.set("org.orrlane.IacApp")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

