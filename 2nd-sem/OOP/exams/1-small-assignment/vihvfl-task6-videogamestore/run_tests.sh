#!/usr/bin/env sh
set -eu

JUNIT_VERSION="1.12.2"
JUNIT_JAR="lib/junit-platform-console-standalone-${JUNIT_VERSION}.jar"

mkdir -p lib out/main out/test

if [ ! -f "$JUNIT_JAR" ]; then
  echo "Downloading JUnit Platform Console ${JUNIT_VERSION}..."
  curl -fsSL "https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/${JUNIT_VERSION}/junit-platform-console-standalone-${JUNIT_VERSION}.jar" -o "$JUNIT_JAR"
fi

echo "Compiling source files..."
javac --release 25 -d out/main src/videogamestore/*.java

echo "Compiling test files..."
javac --release 25 -cp "$JUNIT_JAR:out/main" -d out/test test/videogamestore/*.java

echo "Running tests..."
java -jar "$JUNIT_JAR" --class-path "out/main:out/test" --scan-class-path
