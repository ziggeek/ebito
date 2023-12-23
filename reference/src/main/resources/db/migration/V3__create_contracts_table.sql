CREATE TABLE contracts
(
    id              INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    contract_number INT UNIQUE                  NOT NULL,
    contract_date   DATE                        NOT NULL,
    client_id       INT REFERENCES clients (id) NOT NULL
);
