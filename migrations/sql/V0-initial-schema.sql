CREATE table comment
(
    id      BIGSERIAL PRIMARY KEY,
    time    TIMESTAMP NOT NULL,
    text    TEXT      NOT NULL,
    author  TEXT      NOT NULL,
    news_id BIGINT    NOT NULL
);

CREATE TABLE comment_likes
(
    id         BIGSERIAL PRIMARY KEY,
    comment_id BIGINT    NOT NULL REFERENCES comment (id) ON DELETE CASCADE,
    user_id    BIGINT    NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    UNIQUE (comment_id, user_id)
);