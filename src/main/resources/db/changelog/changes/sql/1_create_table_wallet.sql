CREATE TABLE IF NOT EXISTS wallet
(
    id              UUID PRIMARY KEY,
    balance     NUMERIC(38, 2) NOT NULL
);