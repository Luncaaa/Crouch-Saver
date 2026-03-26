import net.fabricmc.loom.api.LoomGradleExtensionAPI

plugins {
    id("java")
    id("me.modmuss50.mod-publish-plugin") version("latest.release")
    id("com.gradleup.shadow") version("latest.release")
    id("dev.architectury.loom") version("1.13-SNAPSHOT") apply false
    id("architectury-plugin") version("3.5-SNAPSHOT")
}

val maven_group: String by project
val mod_version: String by project
val mod_name: String by project
val minecraft_version: String by project

val releaseInfo by extra { getReleaseData(mod_version) }

architectury {
    minecraft = minecraft_version
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "com.gradleup.shadow")

    group = maven_group
    version = mod_version

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
    }

    tasks {
        compileJava {
            options.encoding = "UTF-8"
            options.release = 21
        }
    }
}

subprojects {
    apply(plugin = "dev.architectury.loom")
    apply(plugin = "architectury-plugin")

    base {
        archivesName.set("${mod_name}-${project.name}")
    }

    repositories {
        mavenCentral()
        mavenLocal()
        maven("https://maven.terraformersmc.com/")
        // maven("https://repo.papermc.io/repository/maven-public/")
    }

    configure<LoomGradleExtensionAPI> {
        silentMojangMappingsLicense()
    }

    dependencies {
        "minecraft"("net.minecraft:minecraft:${minecraft_version}")
        val loom = project.extensions.getByName<LoomGradleExtensionAPI>("loom")
        "mappings"(loom.officialMojangMappings())

        /*implementation("net.kyori:adventure-api:4.26.1")
        implementation("net.kyori:adventure-text-minimessage:4.26.1")
        implementation("net.kyori:adventure-text-serializer-legacy:4.26.1")
        implementation("net.kyori:adventure-text-serializer-gson:4.26.1")*/
    }
}

tasks {
    wrapper {
        distributionType = Wrapper.DistributionType.BIN
    }

    jar {
        enabled = false
    }

    shadowJar {
        enabled = false
    }
	
	register("publishAll") {
        group = "publishing"
        description = "Builds everything and publishes to all platforms."

        dependsOn(":mod:fabric:publishMods")
        dependsOn(":mod:forge:publishMods")
        dependsOn(":mod:neoforge:publishMods")
    }
}