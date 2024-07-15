plugins {
    java
    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.spring.dependency.management) apply false
    alias(libs.plugins.graalvm.buildtools.native) apply false
    alias(libs.plugins.openrewrite.rewrite)
    alias(libs.plugins.gradle.jgitver)
}

group = "org.orrlane"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    rewrite(platform(libs.org.openrewrite.recipe))
    rewrite("org.openrewrite.recipe:rewrite-spring")
    rewrite("org.openrewrite.recipe:rewrite-github-actions")
}

rewrite {
    activeRecipe(
//        "org.orrlane.MavenBestPractices",
//        "org.orrlane.CodeBestPractices",
//        "org.orrlane.GithubActions"
        "org.orrlane.DependencyListExample"
    )
    activeStyle("org.orrlane.NoStarImports")
/*
    exportDatatables = true
*/
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jgitver {
    useDistance = false
    useSnapshot = true
}