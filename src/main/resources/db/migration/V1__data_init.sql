create table "crypto_market_data"(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    symbol VARCHAR(255) NOT NULL,
    date TIMESTAMP NOT NULL,
    high NUMERIC(50,30),
    low NUMERIC(50,30),
    open NUMERIC(50,30),
    close NUMERIC(50,30),
    volume NUMERIC(80,30),
    market_cap NUMERIC(80,30)
);