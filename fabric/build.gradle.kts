plugins {
    id("me.modmuss50.mod-publish-plugin")
}

architectury {
    platformSetupLoomIde()
    fabric()
}

val mod_id: String by project
val mod_name: String by project
val fabric_loader_version: String by project
val modmenu_version: String by project
val minecraft_version_range: String by project

val shadowBundle by configurations.creating {
    isCanBeResolved = true
    isCanBeConsumed = false
}

val common by configurations.creating {
    isCanBeResolved = true
    isCanBeConsumed = false
}

configurations {
    getByName("compileClasspath").extendsFrom(common)
    getByName("runtimeClasspath").extendsFrom(common)
    findByName("developmentFabric")?.extendsFrom(common)
}

dependencies {
    "modImplementation"("net.fabricmc:fabric-loader:${fabric_loader_version}")
    "modImplementation"("com.terraformersmc:modmenu:${modmenu_version}")

    common(project(path = ":common")) { isTransitive = false }
    shadowBundle(project(path = ":common", configuration = "transformProductionFabric"))
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand(
                mapOf(
                    "version" to inputs.properties["version"],
                    "loader_version" to fabric_loader_version,
                    "modmenu_version" to modmenu_version,
                    "minecraft_version_range" to minecraft_version_range,
                    "mod_id" to mod_id,
                    "mod_name" to mod_name
                )
            )
        }
    }

    shadowJar {
        configurations = listOf(shadowBundle)
        archiveClassifier.set("dev-shadow")
    }

    remapJar {
        inputFile.set(shadowJar.flatMap { it.archiveFile })
        destinationDirectory.set(rootProject.layout.buildDirectory.dir("libs"))
    }
}

val data = rootProject.extra["releaseInfo"] as ReleaseData
publishMods {
    file = tasks.remapJar.flatMap { it.archiveFile }
    displayName = data.name
    changelog = data.body
    type = STABLE
    modLoaders.addAll("fabric", "quilt")

    modrinth {
        accessToken = System.getenv("MODRINTH_TOKEN")
        projectId = data.modrinthId
        minecraftVersions.addAll(data.versions)

        optional("modmenu")
    }

    curseforge {
        accessToken = System.getenv("CURSEFORGE_TOKEN")
        projectId = data.curseId
        minecraftVersions.addAll(data.versions)

        javaVersions.add(JavaVersion.VERSION_21)

        clientRequired = true
        serverRequired = false

        optional("modmenu")
        optional("modmenu")
    }
}