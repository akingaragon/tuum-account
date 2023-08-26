CREATE TABLE account
(
    id           BIGSERIAL PRIMARY KEY,
    customer_id  BIGINT                                 NOT NULL,
    country      varchar(2)                             NOT NULL,
    status       varchar(20)                            NOT NULL,
    created_by   varchar(255) DEFAULT 'system_user'     NOT NULL,
    created_date timestamp    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_by   varchar(255),
    updated_date timestamp
);

CREATE TABLE account_balance
(
    id               BIGSERIAL PRIMARY KEY,
    account_id       BIGINT REFERENCES account (id)         NOT NULL,
    available_amount NUMERIC(18, 2)                         NOT NULL,
    currency         VARCHAR(3)                             NOT NULL,
    created_by       varchar(255) DEFAULT 'system_user'     NOT NULL,
    created_date     timestamp    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_by       varchar(255),
    updated_date     timestamp
);

CREATE TABLE transaction
(
    id           BIGSERIAL PRIMARY KEY,
    account_id   BIGINT                                 NOT NULL,
    amount       NUMERIC(18, 2)                         NOT NULL,
    currency     VARCHAR(3)                             NOT NULL,
    direction    VARCHAR(3)                             NOT NULL,
    description  VARCHAR(1000)                          NOT NULL,
    created_by   varchar(255) DEFAULT 'system_user'     NOT NULL,
    created_date timestamp    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_by   varchar(255),
    updated_date timestamp
);