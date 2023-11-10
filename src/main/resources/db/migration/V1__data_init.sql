CREATE TABLE "crypto_market_data"(
    id         SERIAL          PRIMARY KEY  UNIQUE ,
    name       VARCHAR(255)    NOT NULL            ,
    symbol     VARCHAR(255)    NOT NULL            ,
    date       TIMESTAMP       NOT NULL            ,
    high       DECIMAL(50,30)                      ,
    low        DECIMAL(50,30)                      ,
    open       DECIMAL(50,30)                      ,
    close      DECIMAL(50,30)                      ,
    volume     DECIMAL(80,30)                      ,
    market_cap DECIMAL(80,30)
);