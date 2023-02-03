import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.3"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("org.jetbrains.kotlin.plugin.noarg") version "1.5.31"
	id("org.jetbrains.kotlin.plugin.jpa") version "1.5.31"
	kotlin("jvm") version "1.4.30"
	kotlin("plugin.spring") version "1.4.30"
	kotlin("kapt") version "1.4.0"
}

group = "com"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.flywaydb:flyway-core:7.7.0")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("javax.persistence:javax.persistence-api:2.2")
	runtimeOnly("mysql:mysql-connector-java")
	implementation("com.querydsl:querydsl-jpa:5.0.0")
	compile("com.querydsl:querydsl-core:5.0.0.M1")
	kapt("com.querydsl:querydsl-apt:5.0.0:jpa")
	annotationProcessor(group = "com.querydsl", name = "querydsl-apt", classifier = "jpa")
	implementation("io.springfox:springfox-swagger2:2.9.2")
	implementation("io.springfox:springfox-swagger-ui:2.9.2")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

