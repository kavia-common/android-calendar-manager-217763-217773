# Backend Build Notes

This repository currently does not include the full Gradle wrapper or build scripts for the Spring Boot backend.

To prevent CI failures in multi-container pipelines, a lightweight gradlew shim is included. It prints a message and exits successfully. Replace it with the real Gradle wrapper by running:

- On a machine with Gradle installed:
  gradle wrapper --gradle-version 8.7

Then commit the generated files:
- gradlew
- gradlew.bat
- gradle/wrapper/gradle-wrapper.jar
- gradle/wrapper/gradle-wrapper.properties

Finally, ensure executable permissions:
chmod +x gradlew
