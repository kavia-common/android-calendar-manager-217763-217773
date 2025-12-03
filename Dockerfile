# Shim Dockerfile at repo root for CI environments that build from here.
# It delegates to the backend Dockerfile by using the backend directory as context.

# syntax=docker/dockerfile:1

# Stage to copy backend project into temp context and run its Dockerfile
FROM alpine:3.20 AS copier
WORKDIR /src
# Copy only the backend directory into /src/backend
COPY backend /src/backend

# Final image built by invoking the backend Dockerfile inside the subdirectory.
# Many CI systems cannot "FROM" another Dockerfile dynamically, so we emit guidance:
# This Dockerfile is intentionally minimal; to build backend image from repo root, run:
#   docker build -t calendar-backend -f backend/Dockerfile backend
#
# In case CI forcibly uses this file, we prevent silent success:
RUN echo "Please build using: docker build -t calendar-backend -f backend/Dockerfile backend" && exit 1
