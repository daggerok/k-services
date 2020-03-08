pluginManagement {
  repositories {
    gradlePluginPortal()
    mavenCentral()
    maven("https://repo.spring.io/milestone")
  }
  plugins {
    kotlin("jvm") version "1.3.61"
    kotlin("plugin.spring") version "1.3.61"
    id("org.springframework.boot") version "2.3.0.M2"
    id("com.github.johnrengelman.shadow") version "5.2.0"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
  }
}
rootProject.name = "fu-a"
