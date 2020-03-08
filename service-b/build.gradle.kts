plugins {
  kotlin("jvm")
  kotlin("plugin.spring")
  id("org.springframework.boot")
  id("io.spring.dependency-management")
}

allprojects {
  group = "daggerok"
  version = "0.0.1-SNAPSHOT"
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
}

repositories {
  mavenCentral()
  maven("https://repo.spring.io/milestone")
}

dependencies {
  implementation(kotlin("reflect"))
  implementation(kotlin("stdlib-jdk8"))
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.springframework.boot:spring-boot-starter-web")
  testImplementation("org.springframework.boot:spring-boot-starter-test") {
    exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
  }
  testImplementation("io.projectreactor:reactor-test")
}

tasks {
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
      freeCompilerArgs = listOf("-Xjsr305=strict")
      jvmTarget = "1.8"
    }
  }
  withType<Test> {
    useJUnitPlatform()
    testLogging {
      showExceptions = true
      showStandardStreams = true
      events(
          org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
          org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
          org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
      )
    }
  }
}

defaultTasks("clean", "build")
