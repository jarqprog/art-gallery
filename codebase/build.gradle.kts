plugins {
    java
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.3.71"
    kotlin("plugin.spring") version "1.3.71"
}

allprojects {
    group = "com.jarqprog"
    version = "0.0.2-SNAPSHOT"

    repositories {
        mavenCentral()
        jcenter()
    }
}

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "java")
    apply(plugin = "io.spring.dependency-management")
    java.sourceCompatibility = JavaVersion.VERSION_14

    dependencies {
        testImplementation("org.springframework.boot:spring-boot-starter-test:2.3.0.RELEASE") {
            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        }
    }
}
