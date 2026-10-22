#!/usr/bin/env bash
set -e

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BIN_DIR="$DIR/bin"
SRC_DIR="$DIR/src"

compile() {
    mkdir -p "$BIN_DIR"
    find "$SRC_DIR" -name "*.java" > "$BIN_DIR/.sources.txt"
    javac -d "$BIN_DIR" @"$BIN_DIR/.sources.txt"
}

case "$1" in
    test)
        compile
        java -cp "$BIN_DIR" capitaly.TestRunner
        ;;
    clean)
        rm -rf "$BIN_DIR"
        echo "Cleaned bin/"
        ;;
    build)
        compile
        echo "Build successful."
        ;;
    *)
        compile
        if [ "$#" -eq 0 ]; then
            java -cp "$BIN_DIR" capitaly.Main sample_game.txt 5
        else
            java -cp "$BIN_DIR" capitaly.Main "$@"
        fi
        ;;
esac
