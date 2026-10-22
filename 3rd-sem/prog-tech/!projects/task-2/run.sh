#!/usr/bin/env bash
set -euo pipefail

BIN_DIR="bin"
SRC_DIR="src"

build() {
    mkdir -p "$BIN_DIR"
    javac -Xlint:all -d "$BIN_DIR" "$SRC_DIR"/pebble/*.java
}

case "${1:-run}" in
    build)
        build
        echo "Build succeeded."
        ;;
    test)
        build
        java -cp "$BIN_DIR" pebble.TestRunner
        ;;
    clean)
        rm -rf "$BIN_DIR"
        echo "Cleaned $BIN_DIR/"
        ;;
    run|*)
        build
        java -cp "$BIN_DIR" pebble.Main
        ;;
esac
