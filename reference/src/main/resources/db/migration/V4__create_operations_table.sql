CREATE TABLE operations
(
    operation_id   BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    date           DATE                                  NOT NULL,
    time           TIME                                  NOT NULL,
    payment_method VARCHAR                               NOT NULL,
    sum            BIGINT                                NOT NULL,
    client_id      BIGINT REFERENCES clients (client_id) NOT NULL
);