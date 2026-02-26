DROP TABLE IF EXISTS bank_user, accounts, transfer, transfer_history;

CREATE TABLE bank_user
(
    user_id SERIAL PRIMARY KEY,
    user_name VARCHAR(30) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE accounts
(
    account_id SERIAL PRIMARY KEY,
    account_number VARCHAR(50) NOT NULL,
    account_name VARCHAR(30) NOT NULL,
    user_id INT NOT NULL REFERENCES bank_user(user_id),
    balance NUMERIC NOT NULL DEFAULT 0,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE transfer
(
    transfer_id SERIAL PRIMARY KEY,
    sender_id INT NOT NULL REFERENCES accounts(account_id),
    receiver_id INT NOT NULL REFERENCES accounts(account_id),
    amount NUMERIC NOT NULL,
    transfer_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE transfer_history
(
    account_id INT NOT NULL  REFERENCES accounts(account_id),
    amount NUMERIC NOT NULL,
    transfer_type VARCHAR(10) NOT NULL,
    transfer_with VARCHAR(30) NOT NULL,
    transfer_time TIMESTAMP NOT NULL
);

CREATE INDEX idx_accounts_account_number ON accounts(account_number);
CREATE INDEX idx_accounts_user_id_updated_at ON accounts(user_id, updated_at DESC);