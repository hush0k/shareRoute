-- Spring Modulith Events JPA registry: таблица для отслеживания публикации событий модулей.
-- См. https://docs.spring.io/spring-modulith/reference/events.html#jpa

CREATE TABLE event_publication (
    id                       UUID NOT NULL,
    listener_id              TEXT NOT NULL,
    event_type               TEXT NOT NULL,
    serialized_event         TEXT NOT NULL,
    publication_date         TIMESTAMP WITH TIME ZONE NOT NULL,
    completion_date          TIMESTAMP WITH TIME ZONE,
    last_resubmission_date   TIMESTAMP WITH TIME ZONE,
    completion_attempts      SMALLINT,
    status                   TEXT,
    PRIMARY KEY (id)
);

CREATE INDEX event_publication_serialized_event_hash_idx ON event_publication (event_type);
CREATE INDEX event_publication_by_completion_date_idx ON event_publication (completion_date);