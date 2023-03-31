CREATE TABLE IF NOT EXISTS bank_branch
(
    id                 UUID PRIMARY KEY,
    bank_branch_type   VARCHAR(12)      NOT NULL,
    branch_number      VARCHAR(5)       NOT NULL,
    branch_coordinates VARCHAR(20)      NOT NULL,
    city               VARCHAR(20)      NOT NULL,
    branch_address     VARCHAR(50)      NOT NULL,
    is_closed          BOOLEAN          NOT NULL,
    opening_time       TIME             NOT NULL,
    closing_time       TIME             NOT NULL,
    work_at_weekends   BOOLEAN          NOT NULL,
    cash_withdraw      BOOLEAN          NOT NULL,
    money_transfer     BOOLEAN          NOT NULL,
    accept_payment     BOOLEAN          NOT NULL,
    currency_exchange  BOOLEAN          NOT NULL,
    exotic_currency    BOOLEAN          NOT NULL,
    ramp               BOOLEAN          NOT NULL,
    replenish_card     BOOLEAN          NOT NULL,
    replenish_account  BOOLEAN          NOT NULL,
    consultation       BOOLEAN          NOT NULL,
    insurance          BOOLEAN          NOT NULL,
    replenish_without_card BOOLEAN      NOT NULL
);

CREATE TABLE IF NOT EXISTS exchange_rate
(
    id                 UUID             PRIMARY KEY,
    update_at          TIMESTAMP        NOT NULL,
    currency_code      VARCHAR(3)       NOT NULL,
    iso_code           VARCHAR(3)       NOT NULL,
    name               VARCHAR(30)      NOT NULL,
    sign               VARCHAR(10)      NOT NULL,
    unit               INT              NOT NULL,
    buying_rate        DECIMAL(10, 4)   NOT NULL,
    selling_rate       DECIMAL(10, 4)   NOT NULL,
    is_cross           BOOLEAN          NOT NULL
);

CREATE TABLE IF NOT EXISTS error_message
(
    id                 UUID PRIMARY KEY,
    user_id            UUID,
    user_email         VARCHAR(30)      NOT NULL,
    mailing_date       TIMESTAMP        NOT NULL,
    message            TEXT             NOT NULL,
    attachment_id      VARCHAR(30)
);