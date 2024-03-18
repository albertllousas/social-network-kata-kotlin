plugins {
    alias(libs.plugins.jvm)
}

repositories {
    mavenCentral()
}


object Versions {
    const val JUNIT = "5.10.1"
    const val MOCKK = "1.13.9"
    const val ASSERTJ = "3.25.2"
    const val KOTEST_ASSERTIONS = "5.8.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation(libs.junit.jupiter.engine)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("io.mockk:mockk:${Versions.MOCKK}")
    testImplementation("org.junit.jupiter:junit-jupiter:${Versions.JUNIT}")
    testImplementation(group = "org.assertj", name = "assertj-core", version = Versions.ASSERTJ)
    testImplementation(group = "io.kotest", name = "kotest-assertions-core-jvm", version = Versions.KOTEST_ASSERTIONS)
}


java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(11)
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
