CREATE TABLE condition (
    id BIGSERIAL NOT NULL,
    field VARCHAR(255),
    operator INT4,
    toggle_id INT8,
    PRIMARY KEY (id)
);
CREATE TABLE content (
    condition_id INT8 NOT NULL,
    contents VARCHAR(255)
);
CREATE TABLE team (
    id BIGSERIAL NOT NULL,
    name VARCHAR(255),
    PRIMARY KEY (id)
);
CREATE TABLE toggle (
    id BIGSERIAL NOT NULL,
    is_toggled BOOLEAN NOT NULL,
    name VARCHAR(255),
    operator INT4,
    team_id INT8,
    PRIMARY KEY (id)
);

ALTER TABLE condition ADD CONSTRAINT FK_toggle FOREIGN KEY (toggle_id) REFERENCES toggle;
ALTER TABLE content ADD CONSTRAINT FK_condition FOREIGN KEY (condition_id) REFERENCES condition;
ALTER TABLE toggle ADD CONSTRAINT FK_team FOREIGN KEY (team_id) REFERENCES team;