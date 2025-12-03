@echo off
REM Proxy wrapper to backend\gradlew so CI/CD that expects .\gradlew at repo root can still run.
set DIR=%~dp0
call "%DIR%\backend\gradlew.bat" %*
