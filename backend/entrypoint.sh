#!/usr/bin/env sh
set -eu

# PUBLIC_INTERFACE
# Entrypoint for the backend container.
# - Validates that a runnable jar exists in /opt/app/libs
# - Starts the Spring Boot app
# Expected env vars (must be provided at runtime):
#   POSTGRES_URL, POSTGRES_USER, POSTGRES_PASSWORD
# Optional:
#   JAVA_OPTS, SERVER_PORT

JAR_PATH=""
for f in /opt/app/libs/*.jar; do
  if [ -f "$f" ]; then
    # Choose the first jar found (Spring Boot fat jar)
    JAR_PATH="$f"
    break
  fi
done

if [ -z "${JAR_PATH}" ]; then
  echo "ERROR: No jar found in /opt/app/libs" >&2
  ls -la /opt/app/libs || true
  exit 1
fi

# Basic logging of app version
echo "Starting backend using jar: ${JAR_PATH}"
echo "Binding to port: ${SERVER_PORT:-3001}"

# Start the app
exec java ${JAVA_OPTS:-} -Dserver.port="${SERVER_PORT:-3001}" -jar "${JAR_PATH}"
