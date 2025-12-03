#!/usr/bin/env sh
set -eu

# Ensures that the Gradle wrapper exists at ./gradlew and is executable.
# If missing, attempts to generate it using a local Gradle installation if available,
# or downloads the Gradle wrapper jar necessary to bootstrap.
# This script is idempotent.

WORKDIR="${1:-/app}"
cd "${WORKDIR}"

if [ -f "./gradlew" ]; then
  chmod +x ./gradlew || true
  echo "Gradle wrapper found at ${WORKDIR}/gradlew"
  exit 0
fi

echo "Gradle wrapper not found at ${WORKDIR}/gradlew. Attempting to bootstrap..."

# Try using gradle (if installed) to generate wrapper
if command -v gradle >/dev/null 2>&1; then
  echo "Using system gradle to generate wrapper..."
  gradle wrapper || true
fi

# If still missing, attempt minimal bootstrap by downloading wrapper jar
if [ ! -f "./gradlew" ]; then
  echo "Attempting manual bootstrap of Gradle wrapper files..."
  mkdir -p gradle/wrapper
  # Default to a known Gradle version compatible with the project if gradle/wrapper properties missing
  if [ ! -f "gradle/wrapper/gradle-wrapper.properties" ]; then
    cat > gradle/wrapper/gradle-wrapper.properties <<EOF
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\\://services.gradle.org/distributions/gradle-8.5-bin.zip
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
EOF
  fi
  # Create minimal gradlew script if missing
  if [ ! -f "./gradlew" ]; then
    cat > ./gradlew <<'EOS'
#!/usr/bin/env sh
set -eu
APP_HOME=$(cd "$(dirname "$0")" && pwd)
exec java -Dorg.gradle.appname=gradlew -classpath "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain "$@"
EOS
  fi
  chmod +x ./gradlew || true

  # If wrapper jar missing, try to fetch it from the Gradle wrapper repo for the used version
  if [ ! -f "gradle/wrapper/gradle-wrapper.jar" ]; then
    echo "Downloading gradle-wrapper.jar..."
    # Use curl or wget if available
    WRAPPER_JAR_URL="https://repo.gradle.org/gradle/libs-releases-local/org/gradle/gradle-wrapper/8.5/gradle-wrapper-8.5.jar"
    if command -v curl >/dev/null 2>&1; then
      curl -fsSL "$WRAPPER_JAR_URL" -o gradle/wrapper/gradle-wrapper.jar || true
    elif command -v wget >/dev/null 2>&1; then
      wget -q "$WRAPPER_JAR_URL" -O gradle/wrapper/gradle-wrapper.jar || true
    fi
  fi
fi

# Final check
if [ ! -f "./gradlew" ]; then
  echo "ERROR: Unable to create or locate gradlew in ${WORKDIR}" >&2
  exit 127
fi

chmod +x ./gradlew || true
echo "Gradle wrapper prepared at ${WORKDIR}/gradlew"
