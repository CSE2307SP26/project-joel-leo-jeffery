#!/bin/bash

APP_BUILD_DIR="/tmp/project26-main"
TEST_BUILD_DIR="/tmp/project26-test"
JUNIT_JAR="test-lib/junit-platform-console-standalone-1.13.0-M3.jar"

compile_app() {
    javac -d "$APP_BUILD_DIR" src/main/*.java
}

run_app() {
    compile_app
    java -cp "$APP_BUILD_DIR" main.MainMenu
}

run_tests() {
    compile_app
    javac -cp "$APP_BUILD_DIR:$JUNIT_JAR" -d "$TEST_BUILD_DIR" src/test/*.java
    java -jar "$JUNIT_JAR" execute --class-path "$APP_BUILD_DIR:$TEST_BUILD_DIR" --scan-class-path
}

if [ "$1" = "test" ]; then
    run_tests
else
    run_app
fi
