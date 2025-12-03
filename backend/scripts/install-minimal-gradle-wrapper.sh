#!/usr/bin/env sh
set -eu

# Installs a minimal Gradle wrapper into the specified directory (default /app).
# This ensures ./gradlew exists even if not provided in the build context.
TARGET_DIR="${1:-/app}"
mkdir -p "${TARGET_DIR}/gradle/wrapper"

# Create minimal gradlew script if missing
if [ ! -f "${TARGET_DIR}/gradlew" ]; then
  cat > "${TARGET_DIR}/gradlew" <<'EOS'
#!/usr/bin/env sh
set -eu
APP_HOME=$(cd "$(dirname "$0")" && pwd)
# Ensure wrapper jar exists
if [ ! -f "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" ]; then
  echo "gradle-wrapper.jar not found at $APP_HOME/gradle/wrapper/gradle-wrapper.jar" >&2
  exit 127
fi
exec java -Dorg.gradle.appname=gradlew -classpath "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain "$@"
EOS
  chmod +x "${TARGET_DIR}/gradlew" || true
fi

# Write properties with a known compatible version if missing
if [ ! -f "${TARGET_DIR}/gradle/wrapper/gradle-wrapper.properties" ]; then
  cat > "${TARGET_DIR}/gradle/wrapper/gradle-wrapper.properties" <<EOF
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\\://services.gradle.org/distributions/gradle-8.5-bin.zip
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
EOF
fi

# Fetch wrapper jar if missing
if [ ! -f "${TARGET_DIR}/gradle/wrapper/gradle-wrapper.jar" ]; then
  echo "Downloading gradle-wrapper.jar..."
  WRAPPER_JAR_URL="https://repo.gradle.org/gradle/libs-releases-local/org/gradle/gradle-wrapper/8.5/gradle-wrapper-8.5.jar"
  if command -v curl >/dev/null 2>&1; then
    curl -fsSL "$WRAPPER_JAR_URL" -o "${TARGET_DIR}/gradle/wrapper/gradle-wrapper.jar" || true
  elif command -v wget >/dev/null 2>&1; then
    wget -q "$WRAPPER_JAR_URL" -O "${TARGET_DIR}/gradle/wrapper/gradle-wrapper.jar" || true
  fi
fi

# Final validation
if [ ! -f "${TARGET_DIR}/gradlew" ] || [ ! -f "${TARGET_DIR}/gradle/wrapper/gradle-wrapper.jar" ]; then
  echo "ERROR: Could not install a minimal Gradle wrapper into ${TARGET_DIR}" >&2
  exit 127
fi

echo "Minimal Gradle wrapper installed at ${TARGET_DIR}/gradlew"
