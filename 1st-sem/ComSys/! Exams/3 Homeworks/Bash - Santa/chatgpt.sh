#!/bin/bash

# Script: generate_santa_mail.sh
# Purpose: Generates personalized invitations from a template and a database.
# Usage: ./generate_santa_mail.sh <mail_template_file> <database_file>

# --- Input Validation ---
if [ "$#" -ne 2 ]; then
    echo "Usage: $0 <mail_template_file> <database_file>"
    echo "Example: $0 template.txt database.csv"
    exit 1
fi

TEMPLATE_FILE="$1"
DATABASE_FILE="$2"

if [ ! -f "$TEMPLATE_FILE" ]; then
    echo "Error: Template file '$TEMPLATE_FILE' not found."
    exit 1
fi

if [ ! -f "$DATABASE_FILE" ]; then
    echo "Error: Database file '$DATABASE_FILE' not found."
    exit 1
fi

# --- Main Logic ---

echo "--- Starting Mail Generation ---"

# 1. Read the entire template content into a variable
MAIL_TEMPLATE=$(cat "$TEMPLATE_FILE")

# Set the Internal Field Separator (IFS) to a comma for reading the CSV-like data
# Assuming the data fields are separated by a comma (or whitespace if you prefer, but comma is explicit)
# If your fields are separated by something else (like a tab or space), adjust IFS
# For this example, let's assume they are separated by commas for clarity.
IFS=','

# 2. Process the database file line by line
# The loop reads three fields: name, address, and time from each line
while read -r name address time || [ -n "$name" ]; do
    
    # Skip lines that are entirely empty
    if [ -z "$name" ] && [ -z "$address" ] && [ -z "$time" ]; then
        continue
    fi

    # 3. Clean up the data (remove leading/trailing whitespace which sometimes happens with read)
    # Using 'sed' for trimming would be more robust, but this is a common bash-only approach:
    name=$(echo "$name" | xargs)
    address=$(echo "$address" | xargs)
    time=$(echo "$time" | xargs)

    # 4. Substitute Placeholders
    # The 'name' substitution (the first one) is done on the full template.
    # Subsequent substitutions are done on the result of the previous one.
    
    # Substitute <name>
    INVITATION="${MAIL_TEMPLATE//<name>/$name}"
    
    # Substitute <address>
    INVITATION="${INVITATION//<address>/$address}"
    
    # Substitute <time>
    INVITATION="${INVITATION//<time>/$time}"

    # 5. Print Output (Personalized Invitation)
    echo "" # Add a newline for separation
    echo "********************************************"
    echo "To: $name"
    echo "********************************************"
    echo -e "$INVITATION" # Use -e to interpret any escape sequences in the template
    echo "--------------------------------------------"

done < "$DATABASE_FILE"

# Reset IFS to its default value
IFS=$' \t\n'

echo ""
echo "--- Mail Generation Complete ---"