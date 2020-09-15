CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS event_descriptor(
    id UUID PRIMARY KEY NOT NULL,
    art_id UUID NOT NULL,
    version INTEGER NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    type VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    payload TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS art_descriptor(
    id UUID PRIMARY KEY NOT NULL,
    art_id UUID NOT NULL,
    version INTEGER NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    payload TEXT NOT NULL
);
