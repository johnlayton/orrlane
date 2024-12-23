buildscript {
    extra.apply{
        set("opentelemetry.version", "1.40.0")
    }
}

plugins {
    java
    application
    distribution
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.graalvm.buildtools.native)
    alias(libs.plugins.openapi.gradle.plugin)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform(libs.opentelemetry.instrumentation.bom))
    implementation(platform(libs.opentelemetry.bom))
    implementation(libs.spring.boot.starter)
    implementation(libs.logstash.logback.encoder)
//    implementation(libs.opentelemetry.spring.boot.starter)
    implementation("io.opentelemetry:opentelemetry-api");
    implementation("io.opentelemetry:opentelemetry-sdk");
    implementation("io.opentelemetry:opentelemetry-exporter-logging");
    implementation("io.opentelemetry.semconv:opentelemetry-semconv:1.29.0-alpha");
    implementation("io.opentelemetry:opentelemetry-sdk-extension-autoconfigure");
    if (project.hasProperty("native")) {
        implementation(libs.aws.serverless.java.container.springboot3)
    } else {
        implementation(libs.spring.boot.starter.web)
        implementation(libs.spring.boot.starter.actuator)
        implementation(libs.springdoc.openapi.starter.webmvc.ui)
    }
    testImplementation(libs.spring.boot.starter.test)
    testRuntimeOnly(libs.junit.platform.launcher)
}

application {
    mainClass = "org.orrlane.DemoApplication"
}

distributions {
    create("custom") {
        contents {
            into("/")
            from(project.layout.projectDirectory.dir("src/main/scripts"))
            from(tasks.nativeCompile)
        }
    }
}

openApi {
    outputFileName.set("openapi.yaml")
    apiDocsUrl.set("http://localhost:8080/v3/api-docs.yaml")
//    forkProperties = "-Dspring.profiles.active=zipkin"
}

graalvmNative {
    binaries {
        all {
        }
        named("main") {
            buildArgs.add("--verbose")
            buildArgs.add("--enable-preview")
            buildArgs.add("-march=compatibility")
            buildArgs.add("--enable-url-protocols=http")
        }
    }
}

task<Copy>("createFunctionZip") {
    from(tasks.named("customDistZip"))
    into(project.layout.buildDirectory)
    rename { _ : String -> "function.zip" }
}

//project.tasks.fo

tasks.forkedSpringBootRun {
    dependsOn(
        tasks.named("processAotResources"),
        tasks.named("compileAotJava"))
}

//tasks {
//    forkedSpringBootRun {
//        dependsOn(tasks.named("processAotResources"))
//        doNotTrackState("See https://github.com/springdoc/springdoc-openapi-gradle-plugin/issues/102")
//    }
//}