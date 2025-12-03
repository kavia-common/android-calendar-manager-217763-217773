#!/bin/bash
cd /home/kavia/workspace/code-generation/android-calendar-manager-217763-217773/backend
./gradlew checkstyleMain
LINT_EXIT_CODE=$?
if [ $LINT_EXIT_CODE -ne 0 ]; then
   exit 1
fi

