# Shim Dockerfile at repo root for CI environments that (incorrectly) try to build from here.
# IMPORTANT: The backend image MUST be built with backend/ as the build context and backend/Dockerfile as the file.
#
# Correct command:
#   docker build -t calendar-backend -f backend/Dockerfile backend
#
# This file intentionally fails to avoid accidental builds from repo root, which could miss files
# and accidentally reference other containers. It DOES NOT copy or cd into any database paths.

# syntax=docker/dockerfile:1

FROM alpine:3.20
RUN echo "Build aborted: use 'docker build -t calendar-backend -f backend/Dockerfile backend'" && exit 1
