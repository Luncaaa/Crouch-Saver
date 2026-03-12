val enabled_platforms: String by project
val fabric_loader_version: String by project

architectury {
    common(enabled_platforms.split(","))
}

loom {
    accessWidenerPath = file("src/main/resources/crouchsaver.accesswidener")
}

dependencies {
    // We depend on Fabric Loader here to use the Fabric @Environment annotations,
    // which get remapped to the correct annotations on each platform.
    // Do NOT use other classes from Fabric Loader.
    "modImplementation"("net.fabricmc:fabric-loader:${fabric_loader_version}")
}