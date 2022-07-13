plugins {
    `java-library`
}

repositories {
    mavenCentral()
    maven {
        name = "JFrogBintray"
        url = uri("https://kkafara.jfrog.io/artifactory/kkafara-gradle-release-local/")
    }
}

dependencies {
    testImplementation("junit:junit:4.13.2")

    api("com.kkafara.rt:result-type:1.0.3")

    implementation("com.google.guava:guava:30.1.1-jre")
    implementation("org.jetbrains:annotations:23.0.0")
}
