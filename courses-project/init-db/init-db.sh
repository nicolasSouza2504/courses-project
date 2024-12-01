#!/bin/bash

until pg_isready -h "localhost" -p "$POSTGRES_PORT" -U "$POSTGRES_USER" ; do
    echo "Waiting for PostgreSQL to be ready..."
    sleep 2
done

echo "PostgreSQL is ready!"

psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" -f /docker-entrypoint-initdb.d/init-db.sql
