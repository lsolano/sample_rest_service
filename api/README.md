# API Read Me...

Common tasks

## Run from command line (requires maven3 and Java 8 both on your PATH)
`mvn compile exec:java`


## Maven project creation
With maven3 and the JDK 1.8 installed, run from the command line the following command:
`mvn archetype:generate -DarchetypeArtifactId=jersey-quickstart-grizzly2 -DarchetypeGroupId=org.glassfish.jersey.archetypes -DinteractiveMode=false -DgroupId=com.malpeza -DartifactId=department-service -Dpackage=com.malpeza -DarchetypeVersion=2.22.1`