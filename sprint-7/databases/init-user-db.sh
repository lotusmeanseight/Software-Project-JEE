#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER sep;
    CREATE DATABASE classicmodels;
    CREATE DATABASE blz;
    GRANT ALL PRIVILEGES ON DATABASE classicmodels TO sep;
    GRANT ALL PRIVILEGES ON DATABASE blz TO sep;
EOSQL