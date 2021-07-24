plugins {
    java
    `java-library`
    `maven-publish`

    idea
    eclipse
}

the<JavaPluginExtension>().toolchain {
    languageVersion.set(JavaLanguageVersion.of(16))
}

configurations.all {
    attributes.attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, 16)
}

tasks.compileJava.configure {
    options.release.set(8)
}

group = "com.bekvon"
version = "5.0.0.1"

repositories {
    mavenCentral()
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
    flatDir {
        dirs = setOf(file("libs"), file("$rootDir/libs"))
    }
    maven { url = uri("https://repo.mikeprimm.com/") }
    maven { url = uri("https://repo.essentialsx.net/releases/") }
    maven { url = uri("https://maven.enginehub.org/repo/") }
    maven { url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/") }
    maven { url = uri("https://ci.ender.zone/plugin/repository/everything/") }
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://maven.elmakers.com/repository/") }
}

dependencies {
    compileOnlyApi("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
    compileOnly("us.dynmap:dynmap-api:3.0-SNAPSHOT") { isTransitive = false }
    compileOnly("com.github.cryptomorin:kingdoms:1.10.19.2") // apparently outdated?
    compileOnly("net.ess3:EssentialsX:2.18.2")
    compileOnly("com.sk89q.worldedit:worldedit-core:7.2.6-SNAPSHOT")
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.2.6-SNAPSHOT") { isTransitive = false }
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.6-SNAPSHOT") { isTransitive = false }
    compileOnly("com.sk89q.worldguard:worldguard-core:7.0.6-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.10.10")
    compileOnly("com.griefcraft.lwc:LWCX:2.2.6")
    compileOnly("net.luckperms:api:5.3")
    compileOnly("com.github.Slimefun:Slimefun4:RC-26")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7") { isTransitive = false }
    compileOnly("de.bananaco:bPermissions:v2.12-DEV") { isTransitive = false }

    //What you need to add into a `libs` folder in the project, use the links from the README.md
    compileOnly(":iConomy")
    compileOnly(":CMI")
    compileOnly(":CMILib1.0.3.0")
    compileOnly(":CrackShot")

}

val javadocDir = rootDir.resolve("docs").resolve("javadoc")
tasks {
    val assembleTargetDir = create<Copy>("assembleTargetDirectory") {
        destinationDir = rootDir.resolve("target")
        into(destinationDir)
        from(withType<Jar>())
    }
    named("build") {
        dependsOn(assembleTargetDir)
    }

    named<Delete>("clean") {
        doFirst {
            rootDir.resolve("target").deleteRecursively()
            javadocDir.deleteRecursively()
        }
    }

    compileJava {
        options.compilerArgs.addAll(arrayOf("-Xmaxerrs", "1000"))
        options.compilerArgs.add("-Xlint:all")
        for (disabledLint in arrayOf("processing", "path", "fallthrough", "serial"))
            options.compilerArgs.add("-Xlint:$disabledLint")
        options.isDeprecation = true
        options.encoding = "UTF-8"
    }

    javadoc {
        val opt = options as StandardJavadocDocletOptions
        opt.addStringOption("Xdoclint:none", "-quiet")
        opt.tags(
                "apiNote:a:API Note:",
                "implSpec:a:Implementation Requirements:",
                "implNote:a:Implementation Note:"
        )
        opt.destinationDirectory = javadocDir
        opt.addBooleanOption("html5", true)
    }
}

java {
    withSourcesJar()
    withJavadocJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

            pom {

                scm {
                    url.set("https://github.com/NotMyFault/Residence")
                    connection.set("scm:https://NotMyFault@github.com/NotMyFault/Residence.git")
                    developerConnection.set("scm:git://github.com/NotMyFault/Residence.git")
                }
            }
        }
    }

    repositories {
        mavenLocal()
    }
}
