This folder contains 3 subfolder.

1. plugin -- a fodler with the TG plugin for Eclipse.

This plugin is used to assist with generation of domain models and UI.

There are two jar files:
tg-eclipse-plugin_1.4.6.202011092103.jar -- the main plugin file, which may get updated in the future.
org.apache.commons.lang3_3.11.0.jar -- a plugin dependency.

#Installing or updating the plugin in your Eclipse IDE

Under Linux or Windows, copy both jar files from this folder to "dropins" under the main folder where Eclipse is installed (e.g. "eclipse") and restart Eclipse to load the TG plugin.
If you're updating the plugin, make sure you remove existing file tg-eclipse-plugin_[version and date].jar from "dropins" first. Otherwise there would be a conflict of plugin versions.

Under macOS, Eclipse IDE is distributed as the "Eclipse.app" package. Folder "dropins" is part of this packages.
In order to copy the plugin files to "dropins", you need to pen Eclipse.app with option "Show Package Contents" in Finder.
This way you will be able to access folder "Contents/Eclipse/dropins" and copy the plugin files there.
As per earlier remark, you need to remove the previous plugin jar file in case you're updating the TG plugin.

2. launch-configurations -- contains Eclipse Launch configurations for standard demo application "Helsinki Airport".

These configuration can be imported into Eclipse IDE using the import functionality.

3. code-templates -- contains code templates to streamline creating and modifications of entity models when working with TG.