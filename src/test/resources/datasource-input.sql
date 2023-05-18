CREATE TABLE datasource_input (
    id          BIGINT      NOT NULL PRIMARY KEY,
    title       VARCHAR,
    timestamp   TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    latitude    FLOAT       NOT NULL DEFAULT 0,
    longitude   FLOAT       NOT NULL DEFAULT 0,
    enabled     BOOLEAN     NOT NULL DEFAULT TRUE
);

INSERT INTO datasource_input (id, title, timestamp, latitude, longitude, enabled)
VALUES  (100, 'First record', '2023-05-17T21:30:00Z', -23.3078619, -51.1734913, true),
        (200, 'Second record', '2023-05-18T10:00:00Z', -23.323474, -51.189279, true),
        (300, 'Third record', '2023-05-19T00:00:00Z', -23.317019, -51.149444, false);