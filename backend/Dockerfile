# Multi-stage build for Spring Boot backend (Java/Kotlin)
# Stage 1: Build
FROM eclipse-temurin:17-jdk-alpine AS builder

# Ensure workdir exists
RUN mkdir -p /app
WORKDIR /app

# Install required tools
RUN apk add --no-cache bash coreutils git curl wget

# Last-resort inline bootstrap: create a functional Gradle wrapper in /app before any COPY
RUN mkdir -p /app/gradle/wrapper && \
    echo '#!/usr/bin/env sh\nset -eu\nAPP_HOME=$(cd \"$(dirname \"$0\")\" && pwd)\nif [ ! -f \"$APP_HOME/gradle/wrapper/gradle-wrapper.jar\" ]; then echo \"gradle-wrapper.jar missing\" >&2; exit 127; fi\nexec java -Dorg.gradle.appname=gradlew -classpath \"$APP_HOME/gradle/wrapper/gradle-wrapper.jar\" org.gradle.wrapper.GradleWrapperMain \"$@\"' > /app/gradlew && \
    chmod +x /app/gradlew && \
    ( [ -f /app/gradle/wrapper/gradle-wrapper.properties ] || echo 'distributionBase=GRADLE_USER_HOME\ndistributionPath=wrapper/dists\ndistributionUrl=https\\://services.gradle.org/distributions/gradle-8.5-bin.zip\nzipStoreBase=GRADLE_USER_HOME\nzipStorePath=wrapper/dists' > /app/gradle/wrapper/gradle-wrapper.properties ) && \
    ( [ -f /app/gradle/wrapper/gradle-wrapper.jar ] || wget -q -O /app/gradle/wrapper/gradle-wrapper.jar https://repo.gradle.org/gradle/libs-releases-local/org/gradle/gradle-wrapper/8.5/gradle-wrapper-8.5.jar || curl -fsSL https://repo.gradle.org/gradle/libs-releases-local/org/gradle/gradle-wrapper/8.5/gradle-wrapper-8.5.jar -o /app/gradle/wrapper/gradle-wrapper.jar )

# Copy Gradle wrapper and build files (if present in context) to leverage caching and override inline wrapper
COPY gradlew /app/gradlew
COPY gradlew.bat /app/gradlew.bat
COPY settings.gradle /app/settings.gradle
COPY settings.gradle.kts /app/settings.gradle.kts
COPY build.gradle /app/build.gradle
COPY build.gradle.kts /app/build.gradle.kts
COPY gradle /app/gradle

# Ensure executable and presence (bootstrap if still missing)
RUN /app/scripts/ensure-gradle-wrapper.sh /app || true

# Pre-download dependencies (no code yet for better caching). Use shim to fallback to system gradle if necessary.
RUN /app/scripts/run-gradle-or-fallback.sh /app --version || true
RUN /app/scripts/run-gradle-or-fallback.sh /app dependencies || true

# Copy source code
COPY src /app/src
COPY config /app/config

# Build the application (skip tests for faster image; tests should run in CI)
RUN /app/scripts/run-gradle-or-fallback.sh /app clean build -x test

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine

ENV APP_HOME=/opt/app
WORKDIR ${APP_HOME}

# Create non-root user
RUN addgroup -S app && adduser -S app -G app

# Copy fat jar from builder
COPY --from=builder /app/build/libs/ /opt/app/libs/

# Copy entrypoint
COPY entrypoint.sh /opt/app/entrypoint.sh
RUN chmod +x /opt/app/entrypoint.sh && chown -R app:app /opt/app

USER app

# Default environment variables (must be overridden in deployment)
# Do NOT hardcode sensitive values; they must come from environment or secrets
ENV SERVER_PORT=3001

EXPOSE 3001

# PUBLIC_INTERFACE
# The container entrypoint starts the Spring Boot application jar discovered in /opt/app/libs.
# It expects the following environment variables to be provided at runtime:
# - POSTGRES_URL: JDBC URL for the PostgreSQL database
# - POSTGRES_USER: Database username
# - POSTGRES_PASSWORD: Database password
# The app itself binds to SERVER_PORT (default 3001).
ENTRYPOINT ["/opt/app/entrypoint.sh"]
