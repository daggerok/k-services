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
  maven("https://repo.spring.io/snapshot")
}

dependencies {
  implementation(kotlin("reflect"))
  implementation(kotlin("stdlib-jdk8"))
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.springframework.boot:spring-boot-starter-webflux")
  // implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.fu:spring-fu-kofu:0.3.0.M1")
  implementation("org.springframework.fu:spring-fu-autoconfigure-adapter:0.3.0.M1")

  implementation("org.springframework.fu:spring-fu-data-mongodb-coroutines:0.0.5")
  implementation("org.springframework.fu:spring-fu-data-r2dbc-coroutines:0.0.5")
  implementation("org.springframework.fu:spring-fu-webflux-coroutines:0.0.5")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
}

// configurations.all {
//   exclude(module = "jakarta.validation-api")
//   exclude(module = "hibernate-validator")
//   if (project.hasProperty("graal")) {
//     exclude(module = "netty-transport-native-epoll")
//     exclude(module = "netty-transport-native-unix-common")
//     exclude(module = "netty-codec-http2")
//   }
// }

tasks {
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
      freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=enable")
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
