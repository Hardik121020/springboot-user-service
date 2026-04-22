CREATE TABLE users (
    user_id     VARCHAR(50) PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    email       VARCHAR(150) NOT NULL,
    phone       VARCHAR(20),
    created_at  TIMESTAMP NOT NULL
);

ALTER TABLE users ADD CONSTRAINT unique_email UNIQUE (email);

CREATE TABLE service_metadata (
    id VARCHAR(255) PRIMARY KEY,
    service_name VARCHAR(255) NOT NULL,
    team_name VARCHAR(255) NOT NULL,
    repo_url VARCHAR(500) NOT NULL
);

CREATE INDEX idx_service_name ON service_metadata(service_name);
CREATE INDEX idx_team_name ON service_metadata(team_name);