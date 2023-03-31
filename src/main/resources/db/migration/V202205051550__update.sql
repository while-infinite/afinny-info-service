TRUNCATE exchange_rate;
ALTER TABLE exchange_rate
    DROP CONSTRAINT if exists exchange_rate_pkey,
    ADD column if not exists id UUID PRIMARY KEY;
