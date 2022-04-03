CREATE TABLE task (
        id BIGSERIAL PRIMARY KEY,
        date_ DATE NOT NULL,
        description TEXT,
        done BOOLEAN NOT NULL DEFAULT FALSE);

CREATE INDEX task_date_idx ON task (date_);
CREATE INDEX task_done_idx ON task (done);