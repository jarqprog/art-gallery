plugins {
    java
//    id("org.springframework.boot") version "2.3.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.3.71"
    kotlin("plugin.spring") version "1.3.71"
}

//tasks.getByName<BootJar>("bootJar") {
//    enabled = false
//}

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
//    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    java.sourceCompatibility = JavaVersion.VERSION_14
}
