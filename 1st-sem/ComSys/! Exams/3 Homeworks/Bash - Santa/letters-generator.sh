#!/usr/bin/env bash

TEMPLATE_FILE=$1
DATABASE_FILE=$2

while read NAME ADDRESS TIME; do
    sed "s/<name>/$NAME/; s/<address>/$ADDRESS/; s/<time>/$TIME/" < "$TEMPLATE_FILE"
    echo -e "\n---"
done < "$DATABASE_FILE"