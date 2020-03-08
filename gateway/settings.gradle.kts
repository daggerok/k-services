pluginManagement {
  repositories {
    gradlePluginPortal()
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
  }
  plugins {
    kotlin("jvm") version "1.3.61"
    kotlin("plugin.spring") version "1.3.61"
    id("org.springframework.boot") version "2.2.5.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
  }
}
rootProject.name = "gateway"
