#!/usr/bin/env sh
set -eu

# Runs Gradle using the wrapper if available; otherwise installs gradle via apk and runs it.
# Usage: run-gradle-or-fallback.sh <gradle args...>
WORKDIR="${1:-/app}"
shift || true
cd "${WORKDIR}"

ARGS="$@"

if [ -x "./gradlew" ]; then
  echo "Using project Gradle wrapper..."
  exec ./gradlew ${ARGS}
fi

echo "Gradle wrapper not found or not executable. Installing system Gradle via apk..."
# Install gradle if not installed
if ! command -v gradle >/dev/null 2>&1; then
  # Update repositories and install gradle
  apk add --no-cache gradle
fi

echo "Running system Gradle: gradle ${ARGS}"
exec gradle ${ARGS}
