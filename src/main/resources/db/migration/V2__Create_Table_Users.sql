CREATE TABLE users (
    id TEXT PRIMARY KEY,
    login TEXT UNIQUE,
    password TEXT,
    role TEXT
);