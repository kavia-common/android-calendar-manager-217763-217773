#!/usr/bin/env sh
# Portable shim to run Gradle via backend/gradlew even if file mode bits were lost.
# Usage: ./gradlew.sh <args>
set -eu

# If invoked via sh, try to re-exec with bash for better compatibility if available.
if [ -z "${BASH_VERSION:-}" ] && command -v bash >/dev/null 2>&1; then
  exec bash "$0" "$@"
fi

SCRIPT_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
WRAPPER="$SCRIPT_DIR/backend/gradlew"

# Ensure wrapper has execute permission if possible
if [ -f "$WRAPPER" ] && [ ! -x "$WRAPPER" ]; then
  chmod +x "$WRAPPER" 2>/dev/null || true
fi

exec "$WRAPPER" "$@"
