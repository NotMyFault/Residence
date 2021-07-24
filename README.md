Residence - Self serve area protection system
=============================================

To build the source yourself, fork the project and put the following dependencies into a `libs` folder in the project:

- CMI: https://www.spigotmc.org/resources/3742/
- iConomy: https://dev.bukkit.org/projects/iconomy-reloadede
- CMILib: https://www.spigotmc.org/resources/87610/
- CrackShot: https://www.spigotmc.org/resources/48301/

Run `$ ./gradlew clean build` if you are done. You can find the output in the `target/` folder.
If you need the plugin, use the jar without -javadoc or sourced. These are for development purposes or if someone wants to
deploy them to their maven repository.

Note: If you want to use the KingdomsX integration, you need to find a working version. The version on spigot is
newer than the one deployed on maven central.
If you want to use the WorldEdit integration, make sure it's up to date with WorldEdit 7 standards, the code base is not.
