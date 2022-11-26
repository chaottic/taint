plugins {
    id("java")
    id("org.quiltmc.loom") version "0.12-SNAPSHOT"
}

group = "com.chaottic"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")

    minecraft("com.mojang:minecraft:1.19.2")
    mappings(loom.layered {
        officialMojangMappings()
    })

    modImplementation("org.quiltmc:quilt-loader:0.17.6")
    modImplementation("org.quiltmc.quilted-fabric-api:quilted-fabric-api:4.0.0-beta.20+0.66.0-1.19.2-SNAPSHOT")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}