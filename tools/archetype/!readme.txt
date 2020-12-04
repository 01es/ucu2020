################################################
####### Installing the TG Maven Archetype ######
################################################

# install archetype locally
# jar and pom should in be in the current directory
# simply execute the following command from the current directory
mvn install:install-file -Dfile=tg-application-archetype-1.4.6-SNAPSHOT.jar -DpomFile=pom.xml

# create or update local archetype catalog
mvn archetype:update-local-catalog

################################################
###### Generate a project for your TG app ######
################################################

# make sure your have the latest TG changes pulled and installed.
# while in the TG source directory
git pull
mvn clean install -DskipTests


# generate the project structure
# for Linux and macOS
mvn -o org.apache.maven.plugins:maven-archetype-plugin:3.1.0:generate \
-DarchetypeGroupId=fielden \
-DarchetypeArtifactId=tg-application-archetype \
-DarchetypeVersion=1.4.6-SNAPSHOT \
-DgroupId=helsinki \
-DartifactId=airport \
-Dversion=1.0-SNAPSHOT \
-Dpackage=helsinki \
-DcompanyName="Helsinki Asset Management Pty. Ltd." \
-DplatformVersion=1.4.6-SNAPSHOT \
-DprojectName="Helsinki Airport Asset Management" \
-DprojectWebSite=https://airport.helsinki.com.ua \
-DsupportEmail=airport_support@helsinki.com.ua \
-DemailSmpt=mail.helsinki.com.ua

# for Windows: \ does not work, all values with dots should be quoted
mvn org.apache.maven.plugins:maven-archetype-plugin:3.1.0:generate -DarchetypeGroupId=fielden -DarchetypeArtifactId=tg-application-archetype -DarchetypeVersion="1.4.6-SNAPSHOT" -DgroupId=helsinki -DartifactId=airport -Dversion="1.0-SNAPSHOT" -Dpackage=helsinki -DcompanyName="Helsinki Asset Management Pty. Ltd." -DplatformVersion="1.4.5-SNAPSHOT" -DprojectName="Helsinki Airport Asset Management" -DprojectWebSite="https://airport.helsinki.com.ua" -DsupportEmail="airport_support@helsinki.com.ua" -DemailSmpt="mail.helsinki.com.ua"

# generate Eclipse project files
mvn eclipse:eclipse -DdownloadJavadoc -DdownloadSources

################################################
##### Configure Eclipse run configurations #####
################################################

Eclipse launch configurations for a typical TG-based app can be created using the following instructions.
The VM arguments are required for db population, the app server and for running unit tests.
If you are using demo project "Helsinki Airport", you can simply import the launch configurations from folder "../eclipse/launch-configurations".

# Eclipse run config to populate domain
Project: airport-web-server
Main class: helsinki.dev_mod.util.PopulateDb
# Arguments
VM arguments:
    -Dlog4j.configurationFile=src/main/resources/log4j2.xml
    -Djava.system.class.loader=ua.com.fielden.platform.classloader.TgSystemClassLoader
    --add-opens java.base/java.lang=ALL-UNNAMED

# Eclipse run config for Web Application
Project: airport-web-server
Main class: helsinki.webapp.StartOverHttp
# Arguments
VM arguments:
    -Dlog4j.configurationFile=src/main/resources/log4j2.xml
    -Djava.system.class.loader=ua.com.fielden.platform.classloader.TgSystemClassLoader
    --add-opens java.base/java.lang=ALL-UNNAMED

# Eclipse run config for Unit Tests
Project: airport-dao
Main class: helsinki.example.PersonnelTest (or else)
# Arguments
VM arguments:
    -Djava.system.class.loader=ua.com.fielden.platform.classloader.TgSystemClassLoader
    --add-opens java.base/java.lang=ALL-UNNAMED