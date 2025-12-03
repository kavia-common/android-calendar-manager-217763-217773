#!/usr/bin/env bash
# Proxy wrapper to backend/gradlew so CI/CD that expects ./gradlew at repo root can still run.
set -euo pipefail
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
exec "${DIR}/backend/gradlew" "$@"
