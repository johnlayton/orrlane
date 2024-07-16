plugins {
    java
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.aws.cdk.lib)
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.engine)
}

application {
    mainClass.set("org.orrlane.IacApp")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

