CREATE table comment
(
    id      BIGSERIAL PRIMARY KEY,
    time    TIMESTAMP NOT NULL,
    text    TEXT      NOT NULL,
    author  TEXT      NOT NULL,
    news_id BIGINT    NOT NULL
)